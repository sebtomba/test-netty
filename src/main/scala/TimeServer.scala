import io.netty.bootstrap.ServerBootstrap
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

object TimeServer {
  def main(args: Array[String]): Unit = {
    new TimeServer(30303).run()
  }
}

class TimeServer(port: Int) {
  def run(): Unit = {
    val bossGroup: EventLoopGroup = new NioEventLoopGroup()
    val workerGroup: EventLoopGroup = new NioEventLoopGroup()

    try {
      val b = new ServerBootstrap()
      b.group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .childHandler(new ChannelInitializer[SocketChannel] {
          def initChannel(ch: SocketChannel): Unit =
            ch.pipeline().addLast(new TimeEncoder(), new TimeServerHandler())
        })
        .option[Integer](ChannelOption.SO_BACKLOG, 128)
        .childOption[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)

      // Bind and start to accept incoming connections.
      val f = b.bind(port).sync()

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel().closeFuture().sync()
    } finally {
      workerGroup.shutdownGracefully
      bossGroup.shutdownGracefully
    }
  }
}
