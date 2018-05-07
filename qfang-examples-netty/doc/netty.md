# netty Bootstrap 过程剖析

- 正常一个 nio api 需要怎样操作
- 对比 netty api 的操作，netty 内部是怎么完成正常 nio 操作的封装
    - selector 在什么地方
    - channel 在什么地方打开
    - channelHandler 是如何工作的
    - ChannelPipeline 是如何工作的
    - EventLoop 是如何工作的
    - SingleThreadEventExecutor 


``` java
static void doSend(int clientNo) throws InterruptedException {
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(65536));
                    ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));

                    ch.pipeline().addLast(new ClientSendHandler(clientNo));
                }
            });
    ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8787).sync();
    ChannelFuture closeFuture = channelFuture.channel().closeFuture();
    closeFuture.addListeners(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            future.channel().close();
            workerGroup.shutdownGracefully();
        }
    });
}
```

设置 channel 类型
`bootstrap.channel(NioSocketChannel.class);`

``` java
public Bootstrap channel(Class<? extends Channel> channelClass) {
    if (channelClass == null) {
        throw new NullPointerException("channelClass");
    }
    return channelFactory(new BootstrapChannelFactory<Channel>(channelClass));
}
```

BootstrapChannelFactory 将传入的 Channel class 类型保存在 `clazz` 字段，`#newChannel()` 方法就是根据传入的 Channel 类型，通过反射调用其构造函数方法。

``` java
BootstrapChannelFactory(Class<? extends T> clazz) {
    this.clazz = clazz;
}

@Override
public T newChannel(EventLoop eventLoop) {
    try {
        Constructor<? extends T> constructor = clazz.getConstructor(EventLoop.class);
        return constructor.newInstance(eventLoop);
    } catch (Throwable t) {
        throw new ChannelException("Unable to create Channel from class " + clazz, t);
    }
}
```

根据我们传入的 Channel 类型为 NioSocketChannel，我们直接找到该类的构造方法

``` java
public NioSocketChannel(EventLoop eventLoop) {
    this(eventLoop, newSocket());
}

private static SocketChannel newSocket() {
    try {
        // 调用 java.nio 创建 SocketChannel
        return SocketChannel.open();
    } catch (IOException e) {
        throw new ChannelException("Failed to open a socket.", e);
    }
}
```

最终会调用到这个 4 个参数的构造方法，在这个构造方法中初始化了 SocketChannelConfig 属性
``` java
public NioSocketChannel(Channel parent, EventLoop eventLoop, SocketChannel socket) {
    super(parent, eventLoop, socket);
    config = new DefaultSocketChannelConfig(this, socket.socket());
}
```

继续调用父类的构造方法，最终在 AbstractChannel 的构造方法中初始化 DefaultChannelPipeline 属性，所以一个 Channel 中一定会绑定一个唯一的 pipeline（pipeline 属性是 final的）。
在 Channel 构造的时候就构造了 pipeline，并且两者是双向关联关系，互相引用。
``` java
protected AbstractChannel(Channel parent, EventLoop eventLoop) {
    this.parent = parent;
    this.eventLoop = validate(eventLoop);
    unsafe = newUnsafe();
    pipeline = new DefaultChannelPipeline(this);
}
```

DefaultChannelPipeline 的构造方法，这里的 channel 保存的是 NioSocketChannel 对象，另外这里还有两个重要的属性 head 和 tail。


``` java
public DefaultChannelPipeline(AbstractChannel channel) {
    this.channel = channel;

    TailHandler tailHandler = new TailHandler();
    tail = new DefaultChannelHandlerContext(this, null, generateName(tailHandler), tailHandler);

    HeadHandler headHandler = new HeadHandler(channel.unsafe());
    head = new DefaultChannelHandlerContext(this, null, generateName(headHandler), headHandler);

    head.next = tail;
    tail.prev = head;
}
```

我们来重点看下 DefaultChannelPipeline 的 head 和 tail 属性，这两个属性都是 DefaultChannelHandlerContext 的对象，构造这两个对象时分别传入了两个不同的 ChannelHandler (headHandler & tailHandler)，我们来看下这两个 ChannelHandler 有什么区别？
由于 netty5 已经没有区分 Inbound/Outbound 概念了，

- TailHandler 里面几乎没有任何操作
- HeadHandler 中重新的所有方法，都调用了相应 unsafe 对应方法 

> HeadHandler 中的 unsafe 属性是来自 AbstractChannel 中的 unsafe 属性，返回 AbstractChannel 中其 unsafe 是由 `#newUnsafe` 这个抽象方法来返回，在继续往子类找，最终会发现这个工厂方法在 `AbstractNioByteChannel.newUnsafe` 被实现，返回的是 NioByteUnsafe 类型对象
``` java
@Override
protected AbstractNioUnsafe newUnsafe() {
    return new NioByteUnsafe();
}
```

我们暂且记住 DefaultChannelPipeline 中有两个 ChannelHandler head & tail 并且 head 和 tail 构成双向链表结构。

DefaultChannelHandlerInvoker
-> io.netty.channel.DefaultChannelPipeline.HeadHandler.connect
-> io.netty.channel.nio.AbstractNioChannel.AbstractNioUnsafe.connect
io.netty.channel.socket.nio.NioSocketChannel.doConnect



我们需要继续确认的就是 `BootstrapChannelFactory#newChannel()` 方法在什么地方被调用。



### DefaultChannelHandlerContext#findContextInbound & #findContextOutbound 

1、fireChannelActive, fireChannelInactive, fireExceptionCaught, fireUserEventTriggered, fireChannelRead, fireChannelReadComplete, fireChannelWritabilityChanged
这些方法调用的都是 head#fireXXX(); -> DefaultChannelHandlerContext#fireXXX();
DefaultChannelHandlerContext#findContextInbound 也即这些方法是从 ChannelPipeline 的 head 开始往后找，一个个执行符合要求的 ChannelHandler
并且这些方法也是 DefaultChannelPipeline.TailHandler 中被覆盖的方法

2、bind, connect, disconnect, close, flush, read, write, writeAndFlush 
这些方法调用的都是 tail#XXX() -> DefaultChannelHandlerContext#XXX();
DefaultChannelHandlerContext#findContextOutbound 也即这些方法是从 ChannelPipeline 的 tail 开始往前找，一个个执行符合要求的 ChannelHandler
并且这些方法都是 DefaultChannelPipeline.HeadHandler 中被覆盖的方法

``` java
// 这个方法是从 ChannelPipeline.head 开始往 tail 方向依次寻找符合条件的 ChannelHandler
private DefaultChannelHandlerContext findContextInbound(int mask) {
    DefaultChannelHandlerContext ctx = this;
    do {
        ctx = ctx.next;
    } while ((ctx.skipFlags & mask) != 0);
    return ctx;
}

// 这个方法是从 ChannelPipeline.tail 开始往 head 方向依次寻找符合条件的 ChannelHandler
private DefaultChannelHandlerContext findContextOutbound(int mask) {
    DefaultChannelHandlerContext ctx = this;
    do {
        ctx = ctx.prev;
    } while ((ctx.skipFlags & mask) != 0);
    return ctx;
}
```



netty4 中的 DefaultChannelHandlerContext 有两个属性 inbound & outbound，用来标识该 ChannelHandler 是用来处理出站/入职事件的

``` java
DefaultChannelHandlerContext(
        DefaultChannelPipeline pipeline, EventExecutor executor, String name, ChannelHandler handler) {
    super(pipeline, executor, name, isInbound(handler), isOutbound(handler));
    if (handler == null) {
        throw new NullPointerException("handler");
    }
    this.handler = handler;
}

@Override
public ChannelHandler handler() {
    return handler;
}

private static boolean isInbound(ChannelHandler handler) {
    return handler instanceof ChannelInboundHandler;
}

private static boolean isOutbound(ChannelHandler handler) {
    return handler instanceof ChannelOutboundHandler;
}
```


为啥 DefaultChannelPipeline.head.skipFlags & mask == 0
因为 DefaultChannelPipeline.head 重写了 bind 方法，另外参考 DefaultChannelHandlerContext.flagsVal 的值的生成
io.netty.channel.DefaultChannelHandlerContext.skipFlags0 方法
ChannelHandlerAdapter 这个里面所有的方法都是 @Skip 标识的

io.netty.bootstrap.Bootstrap#doConnect
io.netty.bootstrap.AbstractBootstrap#initAndRegister  // create Channel & init Channel
	// createChannel: 抽象方法，根据 Bootstrap 类型不同，调用 Bootstrap#createChannel 或者 ServerBootstrap#createChannel
	io.netty.bootstrap.Bootstrap#createChannel // 1. 从 EventLoopGroup 获取 EventLoop; 2. 调用 BootstrapChannelFactory#newChannel -> NioSocketChannel(EventLoop eventLoop) 构造方法 -> NioSocketChannel#newSocket // SocketChannel.open() 返回 java.nio SocketChannel

### AbstractChannel#read 调用到 ChannelPipeline 的链式传递过程
io.netty.channel.AbstractChannel#read
io.netty.channel.DefaultChannelPipeline#read  -> tail.read();
io.netty.channel.DefaultChannelHandlerContext#read  
	-> io.netty.channel.DefaultChannelHandlerContext#findContextOutbound(int mask)  // 从后往前找，找到第一个覆盖了该方法 (mask) 的 ChannelHandler
		next.invoker.invokeRead(next);  // DefaultChannelHandlerContext.invoker.invokeRead(next)
		// DefaultChannelHandlerContext.invoker -> io.netty.channel.DefaultChannelHandlerInvoker
		io.netty.channel.ChannelHandlerInvokerUtil#invokeReadNow -> ChannelHandlerContext.handler().read(ctx);  -> ChannelHandler.read(ctx)

ChannelHandler.read(ctx)
	-> 要么在自己的 ChannelHandler 中实现 read 方法
	-> 要么走到 io.netty.channel.ChannelHandlerAdapter#read 方法，默认实现。基于当前 ChannelHandlerContext 继续往前找到下一个实现了该方法的 ChannelHandlerContext，这样就实现了 ChannelPipeline 的链式传递

`ChannelHandlerAdapter.read` 方法

``` java
@Skip
@Override
public void read(ChannelHandlerContext ctx) throws Exception {
    // DefaultChannelHandlerContext#read -> DefaultChannelHandlerContext#findContextOutbound(int mask)
    // 继续找到前一个覆盖了该方法的 ChannelHandler
    ctx.read();
}
```

### DefaultChannelPipeline 说明


NioEventLoopGroup workerGroup = new NioEventLoopGroup();

NioEventLoopGroup
    this(0); 
    this(nThreads, (Executor) null);
    this(nThreads, executor, SelectorProvider.provider());
    super(nThreads, executor, selectorProvider);

MultithreadEventLoopGroup
    super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, args);
       DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
                       "io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
    
MultithreadEventExecutorGroup
    executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());  // ThreadPerTaskExecutor 实现了 java.util.concurrent.Executor, 
    children = new EventExecutor[nThreads];  // EventExecutor extends EventExecutorGroup extends ScheduledExecutorService extends ExecutorService extends Executor
        children[i] = newChild(executor, args);  // newChild 方法在 NioEventLoopGroup 里面被实现 -> return new NioEventLoop(this, executor, (SelectorProvider) args[0]);
        
MultithreadEventExecutorGroup 中初始化了固定数量的 NioEventLoop （相当于线程）

``` java
NioEventLoop(NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider) {
    super(parent, executor, false);
    if (selectorProvider == null) {
        throw new NullPointerException("selectorProvider");
    }
    provider = selectorProvider;
    selector = openSelector();
}
```

NioEventLoop 里面的关键属性 & 关键方法
    Selector selector;  // nio Selector
    SelectedSelectionKeySet selectedKeys;  // SelectedSelectionKeySet extends AbstractSet<SelectionKey>，SelectionKey 的集合，SelectedSelectionKeySet 内部维护了两个 SelectionKey 集合
    
    #select // Selector#select
    #selectNow
    #register  // 将 channel 注册到 NioEventLoop 关联的 selector
    #run  // 先调用 #select -> #processSelectedKeysOptimized / #processSelectedKeysPlain -> #runAllTasks 

    
bootstrap#channel(NioSocketChannel.class);
bootstrap#connect("127.0.0.1", 8787)
