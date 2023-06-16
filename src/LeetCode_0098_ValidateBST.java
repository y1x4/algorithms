public class LeetCode_0098_ValidateBST {

    /**
     * The number of nodes in the tree is in the range [1, 104].
     * -231 <= Node.val <= 231 - 1
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, long lower, long upper) {
        if (root == null) {
            return true;
        }
        if (root.val <= lower || root.val >= upper) {
            return false;
        }
        return isValidBST(root.left, lower, root.val) && isValidBST(root.right, root.val, upper);
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
        LeetCode_0098_ValidateBST obj = new LeetCode_0098_ValidateBST();
    }
}