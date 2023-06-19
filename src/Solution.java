import java.util.Stack;

public class Solution {


    public String reverseParentheses(String s) {
        if (s.length() <= 2)
            return s;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                int j = i+1, cnt = 1;
                for ( ; j < s.length(); j++) {
                    if (s.charAt(j) == '(')
                        cnt++;
                    else if (s.charAt(j) == ')')
                        cnt--;
                    if (cnt == 0)
                        break;
                }
                StringBuilder temp = new StringBuilder(reverseParentheses(s.substring(i+1, j)));
                for (int k = temp.length() - 1; k >= 0; k--) {
                    sb.append(temp.charAt(k));
                }
                i = j;
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }


    static class Node {
        int data;
        Node next;
    }

    public Node mergeTwoLists(Node n1, Node n2) {
        if (n1 == null) return n2;
        if (n2 == null) return n1;

        Node head = new Node();
        if (n1.data < n2.data) {
            head.data = n1.data;
            head.next = mergeTwoLists(n1.next, n2);
        } else {
            head.data = n2.data;
            head.next = mergeTwoLists(n1, n2.next);
        }
        return head;
    }

    public boolean isMatch(String s, String p) {
        if (s == null || p == null)
            return false;

        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.') {
                    dp[i][j] = dp[i-1][j-1];
                } else if (p.charAt(j-1) == '*') {
                    if (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.') {
                        dp[i][j] = dp[i][j-2] || dp[i-1][j-2] || dp[i-1][j];    // 抵消0、1、多
                    } else {
                        dp[i][j] = dp[i][j-2];
                    }
                }
            }
        }
        return dp[m][n];
    }



    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public void preorder(TreeNode root) {
        if (root == null)
            return;

        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        TreeNode curr;

        while (stack.size() > 0) {
            curr = stack.pop();
            // 根左右
            if (curr.right != null)
                stack.add(curr.right);
            if (curr.left != null)
                stack.add(curr.left);
        }
    }

    public void inorder(TreeNode root) {
        if (root == null)
            return;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || stack.size() > 0) {
            while (curr != null) {
                // 根左右
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            // 左根右
            curr = curr.right;
        }
    }

    public boolean isBalanced(TreeNode root) {
        return depth(root) != -1;
    }

    public int depth(TreeNode root) {
        if (root == null)
            return 0;

        int leftDepth = depth(root.left);
        if (leftDepth == -1)
            return -1;

        int rightDepth = depth(root.right);
        if (rightDepth == -1)
            return -1;

        if (Math.abs(leftDepth - rightDepth) > 1)
            return -1;

        return 1 + Math.max(leftDepth, rightDepth);
    }


    public int translateNum(int num) {
        if (num < 0) return 0;

        else if (num < 2) return 1;
        else {
            return translate(Integer.toString(num));
        }
    }

    private int translate(String s) {
        int n = s.length();
//        int[] dp = new int[n+1];
//        dp[0] = 1;
//        dp[1] = 1;
//
//        for (int i = 2; i <= n; i++) {
//            if ((s.charAt(i-2) - '0') * 10 + (s.charAt(i-1) - '0') <= 25) {
//                dp[i] = dp[i-1] + dp[i-2];
//            } else {
//                dp[i] = dp[i-1];
//            }
//        }
//        return dp[n];

        int f = 1, g = 1, h = -1;
        for (int i = 2; i <= n; i++) {
            if ((s.charAt(i-2) - '0') * 10 + (s.charAt(i-1) - '0') <= 25) {
                h = g + f;
            } else {
                h = g;
            }
            f = g;
            g = h;
        }
        return h;
    }


    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /** 单向链表快速排序 */
    public void quickSort(ListNode head) {
        if (head==null || head.next==null)
            return;
        quickSort(head, null);
    }

    private void quickSort(ListNode head, ListNode tail) {
        if (head == tail || head.next == tail)
            return;
        ListNode pivot = partition(head, tail);
        quickSort(head, pivot);
        quickSort(pivot.next, tail);
    }

    private ListNode partition(ListNode head, ListNode tail) {
        int pivot = head.val;
        ListNode left = head, right = head.next;
        while (right != tail) {
            if (right.val >= pivot) {
                right = right.next;
            } else {
                left.val = right.val;
                left = left.next;
                right.val = left.val;
                right = right.next;
            }
        }
        left.val = pivot;
        return left;
    }



    public static void main(String[] args) {
        System.out.println(new Solution().translateNum(12258));
        Solution solution = new Solution();
        System.out.println(solution.reverseParentheses("(abcd)"));
        System.out.println(solution.reverseParentheses("(u(love)i)"));
        System.out.println(solution.reverseParentheses("(ed(eb(oc))em)"));
        System.out.println(solution.reverseParentheses("a(bcd(mno)p)q"));
    }
}
