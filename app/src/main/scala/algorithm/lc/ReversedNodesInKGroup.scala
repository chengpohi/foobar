package algorithm.lc

import algorithm.lc.AddTwoNumbers.ListNode

object ReversedNodesInKGroup {


  def reverseKGroup(head: ListNode, k: Int): ListNode = {
    if (head == null || head.next == null || k == 1) {
      return head
    }

    val dummy = new ListNode(0)
    dummy.next = head
    var pointer = dummy;

    while (pointer != null) {
      val lastGroup = pointer
      var i = 0
      (1 to k).takeWhile(j => {
        i = i + 1
        pointer = pointer.next
        pointer != null && pointer.next != null
      })

      if (i == k) {
        val nextGroup = pointer.next
        val reversedList = reverse(lastGroup.next, nextGroup)
        pointer = lastGroup.next
        lastGroup.next = reversedList
        pointer.next = nextGroup
      }
    }

    dummy.next
  }

  def reverse(head: ListNode, tail: ListNode): ListNode = {
    if (head == null || head == null) {
      return head
    }

    var ph = head
    var prev: ListNode = null
    var temp: ListNode = null

    while((ph != null) && (ph != tail)) {
      temp = ph.next
      ph.next = prev
      prev = ph
      ph = temp
    }
    prev
  }

  def main(args: Array[String]): Unit = {
    var head = reverseKGroup(new ListNode(1, new ListNode(2)), 2)

    while (head != null){
      println(head.x)
      head = head.next
    }
  }
}
