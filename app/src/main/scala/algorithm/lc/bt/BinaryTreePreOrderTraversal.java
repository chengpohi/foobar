package algorithm.lc.bt;

import algorithm.lc.bt.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreePreOrderTraversal {
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> treeNodes = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();

        treeNodes.push(root);

        while (!treeNodes.isEmpty()) {
            TreeNode pop = treeNodes.pop();
            res.add(pop.val);
            if (pop.left != null) {
                treeNodes.push(pop.left);
            }

            if (pop.right != null) {
                treeNodes.push(pop.right);
            }
        }

        return res;
    }
}
