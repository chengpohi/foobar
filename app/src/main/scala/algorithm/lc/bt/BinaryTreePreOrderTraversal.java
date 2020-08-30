package algorithm.lc.bt;

import algorithm.lc.bt.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * 若二叉树为空则结束返回，否则：
 * （1）访问根结点。
 * （2）前序遍历左子树。
 * 前序遍历
 * 前序遍历
 * （3）前序遍历右子树 。
 * 前序遍历
 */
public class BinaryTreePreOrderTraversal {
    public static List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stacks = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();

        if (Objects.isNull(root)) {
            return res;
        }

        stacks.push(root);

        while(!stacks.isEmpty()) {
            TreeNode pop = stacks.pop();
            res.add(pop.val);

            if (pop.right != null) {
                stacks.push(pop.right);
            }

            if (pop.left != null) {
                stacks.push(pop.left);
            }
        }

        return res;
    }

    public static void main(String[] args) {
    }
}
