package algorithm.lc.bt;

import algorithm.lc.bt.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 后续遍历
 */
public class BinaryTreePostOrderTraversal {
    public static List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Stack<TreeNode> stacks = new Stack<>();

        stacks.push(root);

        while (!stacks.isEmpty()) {
            TreeNode pop = stacks.pop();

            res.add(pop.val);
            if (pop.left != null) {
                stacks.push(pop.left);
            }

            if (pop.right != null) {
                stacks.push(pop.right);
            }
        }

        return res;
    }
}
