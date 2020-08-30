package algorithm.lc.bt;

import algorithm.lc.bt.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 层次遍历
 */
public class BinaryTreeLevelOrderTraversal {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        ArrayList<TreeNode> list = new ArrayList<>();

        if (Objects.isNull(root)) {
            return res;
        }
        list.add(root);

        while (!list.isEmpty()) {
            ArrayList<Integer> cur = new ArrayList<>();
            ArrayList<TreeNode> nextList = new ArrayList<>();
            for (TreeNode treeNode : list) {
                cur.add(treeNode.val);

                if (treeNode.left != null) {
                    nextList.add(treeNode.left);
                }

                if (treeNode.right != null) {
                    nextList.add(treeNode.right);
                }
            }

            list = nextList;
            res.add(cur);
        }

        return res;
    }
}
