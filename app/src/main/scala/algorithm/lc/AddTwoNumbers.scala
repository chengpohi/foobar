package algorithm.lc

object AddTwoNumbers {

  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    var head = new ListNode(0)
    var (q, p) = (l1, l2)
    var carry = 0
    var curr = head

    while (q != null || p != null || carry != 0) {
      var a = 0
      var b = 0

      if (p != null) {
        a = p.x
        p = p.next
      }

      if (q != null)  {
        b = q.x
        q = q.next
      }

      val res = (a + b + carry) % 10
      carry = (a + b + carry) / 10

      curr = new ListNode(res)
      curr = curr.next
    }

    head.next
  }


  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

}
