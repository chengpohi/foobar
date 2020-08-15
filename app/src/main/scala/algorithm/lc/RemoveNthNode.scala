package algorithm.lc

object RemoveNthNode {
  def removeNthFromEnd(head: ListNode, n: Int): ListNode = {
    val dummyNode = new ListNode(0, null)
    var p = dummyNode
    var q = dummyNode
    dummyNode.next = head

    (1 to n + 1).foreach(i => {
      q = q.next
    })

    while (q != null) {
      p = p.next
      q = q.next
    }
    val deleteNode = p.next
    p.next = deleteNode.next

    dummyNode.next
  }

  def main(args: Array[String]): Unit = {
    val node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))))

    val node1 = removeNthFromEnd(node, 2)
    var p = node1

    while (p != null) {
      println(p.x)
      p = p.next
    }

  }

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

}
