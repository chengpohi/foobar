package algorithm.lc

import scala.collection.mutable

object BinaryTreePreOrderTraversal {
  /**
   * 前序遍历
   */
  def preorderTraversal(root: TreeNode): List[Int] = {
    var nodes = new mutable.Stack[TreeNode]()
    var res = List[Int]()
    if (root == null) {
      return res
    }
    nodes.push(root)
    while(nodes.nonEmpty) {
      val node = nodes.pop()
      res = node.value +: res
      if (node.left != null) nodes.push(node.left)
      if (node.right != null) nodes.push(node.right)
    }
    res
  }

  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }

}
