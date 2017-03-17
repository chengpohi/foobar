package algorithm

/**
  * scala99
  * Created by chengpohi on 10/7/15.
  */
case class Node[T](left: Option[Node[T]],
                   right: Option[Node[T]],
                   value: Int,
                   key: Option[T])
