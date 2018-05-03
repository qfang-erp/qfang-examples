# netty Bootstrap 过程剖析

- 正常一个 nio api 需要怎样操作
- 对比 netty api 的操作，netty 内部是怎么完成正常 nio 操作的封装
    - selector 在什么地方
    - channel 在什么地方打开
    - channelHandler 是如何工作的
    - ChannelPipeline 是如何工作的
    - EventLoop 是如何工作的


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



io.netty.channel.DefaultChannelHandlerContext.findContextOutbound
``` java
    private DefaultChannelHandlerContext findContextOutbound(int mask) {
        DefaultChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while ((ctx.skipFlags & mask) != 0);
        return ctx;  // 返回的是 DefaultChannelPipeline.head
    }

```

为啥 DefaultChannelPipeline.head.skipFlags & mask == 0
因为 DefaultChannelPipeline.head 重写了 bind 方法，另外参考 DefaultChannelHandlerContext.flagsVal 的值的生成
io.netty.channel.DefaultChannelHandlerContext.skipFlags0 方法
ChannelHandlerAdapter 这个里面所有的方法都是 @Skip 标识的

