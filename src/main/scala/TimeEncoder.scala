import io.netty.buffer.ByteBuf
import io.netty.channel._
import io.netty.handler.codec.MessageToByteEncoder

class TimeEncoder extends MessageToByteEncoder[UnixTime] {

  def encode(ctx: ChannelHandlerContext, msg: UnixTime, out: ByteBuf): Unit =
    out.writeInt(msg.value.toInt)
}
