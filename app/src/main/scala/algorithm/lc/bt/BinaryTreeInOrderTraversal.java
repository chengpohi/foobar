package algorithm.lc.bt;

import algorithm.lc.bt.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 中序遍历首先遍历左子树，然后访问根结点，最后遍历右子树。若二叉树为空则结束返回，否则：
 * （1）中序遍历左子树
 * （2）访问根结点
 * （3）中序遍历右子树
 * 如右图所示二叉树，中序遍历结果：DBEAFC
 */
public class BinaryTreeInOrderTraversal {

    /**
     * 中序遍历的顺序为左-根-右，具体算法为：
     * <p>
     * 从根节点开始，先将根节点压入栈 然后再将其所有左子结点压入栈，取出栈顶节点，保存节点值 再将当前指针移到其右子节点上，若存在右子节点，则在下次循环时又可将其所有左子结点压入栈中
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stacks = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();

        TreeNode cur = root;

        while(cur != null || !stacks.isEmpty()) {

            if (cur != null) {
                stacks.push(cur);
                cur = cur.left;
            } else {
                TreeNode pop = stacks.pop();
                stacks.push(pop.right);
                cur = pop.right;
                res.add(pop.val);
            }
        }

        return res;
    }
}
