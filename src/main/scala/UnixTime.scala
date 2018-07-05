import java.util.Date

class UnixTime(val value: Long) {

  override def equals(obj: scala.Any): Boolean =
    obj.isInstanceOf[Long] && obj.asInstanceOf[Long] == value

  override def hashCode(): Int = value.hashCode()

  override def toString: String =
    new Date((value - 2208988800L) * 1000L).toString
}

object UnixTime {
  def apply(): UnixTime = new UnixTime(System.currentTimeMillis() / 1000L + 2208988800L)
  def apply(value: Long): UnixTime = new UnixTime(value)
}
