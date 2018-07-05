import io.netty.bootstrap.Bootstrap
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

object TimeClient {
  def main(args: Array[String]): Unit = {
    val workerGroup: EventLoopGroup = new NioEventLoopGroup()

    try {
      val b = new Bootstrap()
      b.group(workerGroup)
      b.channel(classOf[NioSocketChannel])
      b.option[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)
      b.handler(new ChannelInitializer[SocketChannel] {
        def initChannel(ch: SocketChannel): Unit =
          ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler())
      })

      // Start the client.
      val f = b.connect("localhost", 30303).sync()
      // Wait until the connection is closed.
      f.channel().closeFuture().sync()
    } finally {
      workerGroup.shutdownGracefully()
    }
  }
}
