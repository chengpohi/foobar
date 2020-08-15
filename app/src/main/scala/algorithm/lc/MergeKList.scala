package algorithm.lc

import scala.collection.mutable

object MergeKList {

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

  def mergeKLists(lists: Array[ListNode]): ListNode = {
    implicit val ol: Ordering[ListNode] = (x: ListNode, y: ListNode) => y.x - x.x

    val q = mutable.PriorityQueue[ListNode]()(ol)
    lists.filter(i => i != null).foreach(i => q.enqueue(i))

    val head: ListNode = new ListNode(0)
    var cur: ListNode = head

    while (q.nonEmpty) {
      cur.next = q.dequeue()
      cur = cur.next
      if (cur == null) {
        return head.next
      }
      val next = cur.next
      if (null != next) {
        q.enqueue(next)
      }
    }

    head.next
  }

  def main(args: Array[String]): Unit = {
    var head = mergeKLists(Array(
      new ListNode(1, new ListNode(4, new ListNode(5))),
      new ListNode(1, new ListNode(3, new ListNode(4))),
      new ListNode(2, new ListNode(6)))
    )

    while (head != null){
      println(head.x)
      head = head.next
    }
  }
}
