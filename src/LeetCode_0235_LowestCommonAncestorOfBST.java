public class LeetCode_0235_LowestCommonAncestorOfBST {

    /**
     * The number of nodes in the tree is in the range [2, 105].
     * -109 <= Node.val <= 109
     * All Node.val are unique.
     * p != q
     * p and q will exist in the BST.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (true) {
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                break;
            }
        }
        return root;
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
        LeetCode_0235_LowestCommonAncestorOfBST obj = new LeetCode_0235_LowestCommonAncestorOfBST();
    }
}
