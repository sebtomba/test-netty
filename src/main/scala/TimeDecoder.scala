import java.util

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ReplayingDecoder

class TimeDecoder extends ReplayingDecoder[Unit] {
  def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit =
    out.add(UnixTime(in.readUnsignedInt()))
}
