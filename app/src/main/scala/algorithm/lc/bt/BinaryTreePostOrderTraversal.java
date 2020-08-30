package algorithm.lc.bt;

import algorithm.lc.bt.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 若二叉树为空则结束返回， 否则： （1）后序遍历左子树 （2）后序遍历右子树 （3）访问根结点 如右图所示二叉树 后序遍历结果：DEBFCA 后续遍历
 */
public class BinaryTreePostOrderTraversal {
    public static List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Stack<TreeNode> stacks = new Stack<>();

        stacks.push(root);

        while (!stacks.isEmpty()) {
            TreeNode pop = stacks.pop();

            if (pop.left != null) {
                stacks.push(pop.left);
            }

            if (pop.right != null) {
                stacks.push(pop.right);
            }
            res.add(0, pop.val);
        }

        return res;
    }
}
