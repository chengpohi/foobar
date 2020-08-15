package algorithm.lc

object MergeTwoLinkedList {

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

  def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = {
    if (l1 == null)
      return l2
    if (l2 == null)
      return l1

    var newHead: ListNode = null

    if (l1.x < l2.x) {
      newHead = l1
      newHead.next = mergeTwoLists(l1.next, l2)
    } else {
      newHead = l2
      newHead.next = mergeTwoLists(l1, l2.next)
    }
    newHead
  }


  def main(args: Array[String]): Unit = {
    var node = mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(4))), new ListNode(1, new ListNode(3, new ListNode(4))))

    while(node != null) {
      println(node.x)
      node = node.next
    }

  }
}
