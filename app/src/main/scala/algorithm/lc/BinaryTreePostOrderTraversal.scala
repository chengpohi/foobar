package algorithm.lc

import scala.collection.mutable

object BinaryTreePostOrderTraversal {
  /**
   * 后序遍历
   */
  def preorderTraversal(root: TreeNode): List[Int] = {
    var nodes = new mutable.Stack[TreeNode]()
    var res = List[Int]()
    if (root == null) {
      return res
    }
    nodes.push(root)
    while (nodes.nonEmpty) {
      val node = nodes.pop()
      res = res :+ node.value
      if (node.right != null) nodes.push(node.right)
      if (node.left != null) nodes.push(node.left)
    }
    res
  }

  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }

}
