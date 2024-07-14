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

    int max = 0;
    public int longestZigZag(TreeNode root) {
        if (root != null) {
            dfs(root);
        }
        return max;
    }
    private int[] dfs(TreeNode root) {
        int[] res = new int[2];
        if (root.left != null) {
            res[0] = dfs(root.left)[1] + 1;
        }
        if (root.right != null) {
            res[1] = dfs(root.right)[0] + 1;
        }
        max = Math.max(max, Math.max(res[0], res[1]));
        return res;
    }

    int ans = 0;
    public int goodNodes(TreeNode root) {
        dfs(root, Integer.MIN_VALUE);
        return ans;
    }

    private void dfs(TreeNode root, int maxVal) {
        if (root == null) {
            return;
        }
        if (root.val >= maxVal) {
            ans++;
            maxVal = root.val;
        }
        dfs(root.left, maxVal);
        dfs(root.right, maxVal);
    }

    public static void main(String[] args) {
        LeetCode_0235_LowestCommonAncestorOfBST obj = new LeetCode_0235_LowestCommonAncestorOfBST();
    }
}
