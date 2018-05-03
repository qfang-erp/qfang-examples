# netty Bootstrap 过程剖析

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

继续调用父类的构造方法，最终在 AbstractChannel 的构造方法中初始化 DefaultChannelPipeline 属性，一个 Channel 中一定会绑定一个唯一的 pipeline（pipeline 属性是 final的）。
在 Channel 构造的时候就构造了 pipeline，并且两者是双向关联关系，互相引用。
``` java
protected AbstractChannel(Channel parent, EventLoop eventLoop) {
    this.parent = parent;
    this.eventLoop = validate(eventLoop);
    unsafe = newUnsafe();
    pipeline = new DefaultChannelPipeline(this);
}
```


我们需要继续确认的就是 `BootstrapChannelFactory#newChannel()` 方法在什么地方被调用。



