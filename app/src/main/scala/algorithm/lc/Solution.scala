package algorithm.lc

object Solution {
  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    val head = new ListNode(0)
    var carry = 0
    var curr = head
    var (p, q) = (l1, l2)
    while (p != null || q != null || carry != 0) {
      var a = 0
      var b = 0
      if (p != null) {
        a = p.x
        p = p.next
      }

      if (q != null) {
        b = q.x
        q = q.next
      }

      val x = (a + b + carry) % 10
      carry = (a + b + carry) / 10
      curr.next = new ListNode(x)
      curr = curr.next
    }
    head.next
  }

  def main(args: Array[String]): Unit = {
//    val node3 = new ListNode(3)
//    val node4 = new ListNode(4, node3)
    val node2 = new ListNode(1, null)

    val node_5 = new ListNode(9, new ListNode(9))
    val ints = addTwoNumbers(node2, node_5)

    println("-----" * 10)
    println(ints.x) //7
    println(ints.next.x) //0
    println(ints.next.next.x) //8
  }

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

}
