import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class TimeClientHandler extends ChannelInboundHandlerAdapter {
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    val m = msg.asInstanceOf[UnixTime]
    println(m)
    ctx.close()
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace()
    ctx.close()
  }

}
