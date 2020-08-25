package algorithm.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeInOrderTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) { this.val = val; }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 中序遍历的顺序为左-根-右，具体算法为：
     *
     * 从根节点开始，先将根节点压入栈
     * 然后再将其所有左子结点压入栈，取出栈顶节点，保存节点值
     * 再将当前指针移到其右子节点上，若存在右子节点，则在下次循环时又可将其所有左子结点压入栈中
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stacks = new Stack<>();
        TreeNode cur = root;

        while (cur != null || !stacks.isEmpty()) {
            if (cur != null) {
                stacks.push(cur);
                cur = cur.left;
            } else {
                cur = stacks.pop();
                list.add(cur.val);
                cur = cur.right;
            }
        }

        return list;
    }
}
