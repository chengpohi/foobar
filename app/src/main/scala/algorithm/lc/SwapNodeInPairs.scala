package algorithm.lc

import algorithm.lc.AddTwoNumbers.ListNode

object SwapNodeInPairs {

  def swapPairs(head: ListNode): ListNode = {
    val dummyHead = new ListNode(0)
    dummyHead.next = head
    var p = dummyHead

    while (p.next != null && p.next.next != null) {
      val node1 = p.next
      val node2 = node1.next
      val next = node2.next
      p.next = node2
      node2.next = node1
      node1.next = next
      p = node1
    }
    dummyHead.next
  }

  def main(args: Array[String]): Unit = {
    swapPairs(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4)))))
  }
}
