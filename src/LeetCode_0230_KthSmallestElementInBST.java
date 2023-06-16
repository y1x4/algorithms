public class LeetCode_0230_KthSmallestElementInBST {

    /**
     * The number of nodes in the tree is n.
     * 1 <= k <= n <= 104
     * 0 <= Node.val <= 104
     */
    private int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        if (root == null)
            return -1;

        int left = kthSmallest(root.left, k);
        if (left >= 0)
            return left;

        if (--count == 0)
            return root.val;

        return kthSmallest(root.right, k);
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        LeetCode_0230_KthSmallestElementInBST obj = new LeetCode_0230_KthSmallestElementInBST();
    }
}
