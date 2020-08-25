package algorithm.lc;

import algorithm.lc.BinaryTreeInOrderTraversal.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeLevelOrderTraversal {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList();
        List<TreeNode> list = new ArrayList();
        if (root == null) {
            return result;
        }
        list.add(root);
        while (!list.isEmpty()) {
            List<Integer> curList = new ArrayList();
            List<TreeNode> nextList = new ArrayList();
            for (TreeNode cur : list) {
                curList.add(cur.val);
                if (cur.left != null) {
                    nextList.add(cur.left);
                }
                if (cur.right != null) {
                    nextList.add(cur.right);
                }
            }
            list = nextList;
            result.add(curList);
        }
        return result;
    }
}
