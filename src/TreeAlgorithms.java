import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TreeAlgorithms {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 普通二叉树
     * p、q存在于树中且不相同，所有值不相同
     * O(N), O(N)
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == root || q == root)
            return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null)
            return root;
        else
            return left == null ? right : left;
    }

    /**
     * 二叉搜索树
     * O(logN), O(N)
     */
    public static TreeNode BSTLowestCommonAncestorRecursively(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val)
            return BSTLowestCommonAncestorRecursively(root.left, p, q);
        else if (root.val < p.val && root.val < q.val)
            return BSTLowestCommonAncestorRecursively(root.right, p, q);
        else
            return root;
    }

    /**
     * O(logN), O(1)
     */
    public static TreeNode BSTLowestCommonAncestorLoop(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.val > p.val && curr.val > q.val)
                curr = curr.left;
            else if (curr.val < p.val && curr.val < q.val)
                curr = curr.right;
            else
                return root;
        }
        return null;
    }


    /**
     * 124. 二叉树中的最大路径和
     */
    public int maxPathSum(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathSum(root, max);
        return max[0];
    }

    private int maxPathSum(TreeNode root, int[] max) {
        if (root == null)
            return 0;

        int leftSubTreeMaxPathSum = maxPathSum(root.left, max);
        int rightSubTreeMaxPathSum = maxPathSum(root.right, max);
        max[0] = Math.max(max[0], root.val + Math.max(0, leftSubTreeMaxPathSum) + Math.max(0, rightSubTreeMaxPathSum));
        return root.val + Math.max(0, Math.max(leftSubTreeMaxPathSum, rightSubTreeMaxPathSum));
    }


    /**
     * 297.序列化与反序列化二叉树
     */
    public String serialize(TreeNode root) {
        if (root == null)
            return "x";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }

    public TreeNode deserialize(String data) {
        List<String> valList = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserialize(valList);
    }

    private TreeNode deserialize(List<String> valList) {
        String val = valList.remove(0);
        if (val.equals("x"))
            return null;
        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = deserialize(valList);
        root.right = deserialize(valList);
        return root;
    }
}
