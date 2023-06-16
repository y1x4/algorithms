import java.util.*;
import java.util.stream.Collectors;

public class Algorithms {


//    public String longestPalindrome(String s) {
//        if (s == null || s.length() == 0)
//            return "";
//
//        int start = 0, end = 0;
//        for (int i = 0; i < s.length(); i++) {
//            int len1 = expandAroundCenter(s, i, i);
//            int len2 = expandAroundCenter(s, i, i + 1);
//            int len = Math.max(len1, len2);
//            if (len > end - start) {
//                start = i - (len - 1) / 2;
//                end = i + len / 2;
//            }
//        }
//
//        return s.substring(start, end);
//    }
//
//    private int expandAroundCenter(String s, int left, int right) {
//        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
//            left--;
//            right++;
//        }
//        return right - left - 1;
//    }

    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLen = 0, start = 0;
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i < n; i++) {
                int j = i + len - 1;
                if (j >= n) break;
                if (len == 1 || (s.charAt(i) == s.charAt(j) && (len <= 3 || dp[i-1][j-1]))) {
                    dp[i][j] = true;
                    if (len > maxLen) {
                        maxLen = len;
                        start = i;
                    }
                }
            }
        }
        return s.substring(start, start + maxLen);
    }


    public double findMedianSortedArrays(int[] A, int[] B) {
        // to ensure m<=n
        if (A.length > B.length) {
            int[] temp = A;
            A = B;
            B = temp;
        }

        int m = A.length, n = B.length;
        int iMin = 0, iMax = m;
        int halfLen = (m + n + 1) / 2;

        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft;
                if (i == 0) maxLeft = B[j - 1];
                else if (j == 0) maxLeft = A[i - 1];
                else maxLeft = Math.max(A[i - 1], B[j - 1]);

                if ((m + n) % 2 == 1)
                    return maxLeft;

                int minRight;
                if (i == m) minRight = B[j];
                else if (j == n) minRight = A[i];
                else minRight = Math.min(B[j], A[i]);

                return (maxLeft + minRight) / 2.0;
            }
        }

        return 0.0;
    }


    public int maxArea(int[] height) {
        if (height == null || height.length < 2)
            return 0;

        int maxArea = 0;
        int left = 0, right = height.length - 1;

        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            maxArea = Math.max(maxArea, minHeight * (right - left));
            if (height[left] < height[right]) {
                while (left < right && height[left] <= minHeight) {
                    left++;
                }
            } else {
                while (left < right && height[right] <= minHeight) {
                    right--;
                }
            }
        }

        return maxArea;
    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3)
            return res;

        Arrays.sort(nums);

        int n = nums.length, range = n - 2;

        for (int i = 0; i < range; i++) {
            if (nums[i] > 0)
                break;
            if (i > 0 && nums[i] == nums[i - 1])
                continue;

            int left = i + 1, right = n - 1;
            while (left < right) {
                int sum = nums[left] + nums[right] + nums[i];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left + 1] == nums[left])
                        left++;
                    while (left < right && nums[right - 1] == nums[right])
                        right--;
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        return res;
    }


    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        permute(Arrays.stream(nums).boxed().collect(Collectors.toList()), res, 0);
        return res;
    }

    private void permute(List<Integer> nums, List<List<Integer>> res, int i) {
        if (i == nums.size()) {
            res.add(new ArrayList<>(nums));
        } else {
            for (int j = i; j < nums.size(); j++) {
                Collections.swap(nums, i, j);
                permute(nums, res, i + 1);
                Collections.swap(nums, i, j);
            }
        }
    }


    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        if (nums.length == 1)
            return nums[0] == target ? 0 : -1;

        int lo = 0, hi = nums.length - 1, mid;
        while (lo <= hi) {
            mid = lo + ((hi - lo) >> 1);
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] >= nums[0]) {
                if (target >= nums[lo] && target < nums[mid])
                    hi = mid - 1;
                else
                    lo = mid + 1;
            } else {
                if (target > nums[mid] && target <= nums[hi])
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }
        return -1;
    }


    // 输入: num1 = "123", num2 = "456"
    // 输出: "56088"
    // 最多 m+n 位，至少 m+n-1 位
    public String multiply(String num1, String num2) {
        if (num1.charAt(0) == '0' || num2.charAt(0) == '0')
            return "0";

        int m = num1.length(), n = num2.length(), sum = 0;
        int[] result = new int[m + n];

        for (int i = m - 1; i >= 0; i--) {
            int d1 = num1.charAt(i) - '0';
            for (int j = n - 1; j >= 0; j--) {
                sum = d1 * (num2.charAt(j) - '0') + result[i + j + 1];
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }

        StringBuilder sb;
        if (result[0] == 0) {
            sb = new StringBuilder(m + n - 1);
        } else {
            sb = new StringBuilder(m + n);
            sb.append(result[0]);
        }
        for (int i = 1; i < result.length; i++) {
            sb.append(result[i]);
        }

        return sb.toString();
    }


    // 贪心算法
    public int maxSubArray(int[] nums) {
        int currSum = nums[0], maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(currSum + nums[i], nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }

        return maxSum;
    }


    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }

        return dp[n - 1];
    }


    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        int[] dp = new int[n];
        for (int j = 0; j < n; j++) {
            dp[j] = 1;
            if (obstacleGrid[0][j] == 1) {
                for (; j < n; j++) {
                    dp[j] = 0;
                }
            }
        }


        for (int i = 1; i < m; i++) {
            if (obstacleGrid[i][0] == 1)
                dp[0] = 0;
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1)
                    dp[j] = 0;
                else
                    dp[j] += dp[j - 1];
            }
        }

        return dp[n - 1];
    }


    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = (Math.min(dp[j], dp[j - 1]) + grid[i][j]);
            }
        }

        return dp[n - 1];
    }


    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];

        dp[m - 1][n - 1] = Math.max(1, 1 - dungeon[m - 1][n - 1]);

        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(1, dp[m - 1][j + 1] - dungeon[m - 1][j]);
        }
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - dungeon[i][n - 1]);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.max(1, Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j]);
            }
        }

        return dp[0][0];
    }


    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(0);

        int head = 1;
        for (int i = 0; i < n; i++) {
            for (int j = res.size() - 1; j >= 0; j--) {
                res.add(head + res.get(j));
            }
            head <<= 1;
        }
        return res;
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    int maxPathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxSubtreePathSum(root);
        return maxPathSum;
    }

    private int maxSubtreePathSum(TreeNode root) {
        if (root == null)
            return 0;

        int leftMaxSum = maxSubtreePathSum(root.left);
        int rightMaxSum = maxSubtreePathSum(root.right);

        maxPathSum = Math.max(maxPathSum, root.val + Math.max(leftMaxSum, 0) + Math.max(rightMaxSum, 0));

        return root.val + Math.max(Math.max(leftMaxSum, 0), Math.max(rightMaxSum, 0));
    }


    public int findKthLargest(int[] nums, int k) {
        int n = nums.length, lo = 0, hi = n - 1;

        while (lo < hi) {
            int idx = partition(nums, lo, hi);
            int cmp = n - k - idx;
            if (cmp == 0)
                return nums[idx];
            else if (cmp > 0)
                lo = idx + 1;
            else
                hi = idx - 1;
        }

        return nums[lo];
    }

    private int partition(int[] nums, int lo, int hi) {
        int pivot = nums[lo];
        while (lo < hi) {
            while (lo < hi && nums[hi] >= pivot)
                hi--;
            nums[lo] = nums[hi];
            while (lo < hi && nums[lo] < pivot)
                lo++;
            nums[hi] = nums[lo];
        }
        nums[lo] = pivot;

        return lo;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode head2 = slow.next;
        slow.next = null;
        return mergeList(sortList(head), sortList(head2));
    }

    private ListNode mergeList(ListNode head1, ListNode head2) {
//        if (head1 == null)
//            return head2;
//        if (head2 == null)
//            return head1;
//        if (head1.val <= head2.val) {
//            head1.next = mergeList(head1.next, head2);
//            return head1;
//        } else {
//            head2.next = mergeList(head1, head2.next);
//            return head2;
//        }


        ListNode dummy = new ListNode(-1), curr = dummy;

        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                curr.next = head1;
                head1 = head1.next;
            } else {
                curr.next = head2;
                head2 = head2.next;
            }

            curr = curr.next;
        }
        if (head1 != null)
            curr.next = head1;
        if (head2 != null)
            curr.next = head2;
        return dummy.next;
    }


    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || stack.size() > 0) {
            while (curr != null) {
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (--k == 0)
                return curr.val;
            curr = curr.right;
        }

        return -1;
    }


    public int kthSmallest(TreeNode root, int[] k) {
        if (root == null)
            return -1;
        int val = kthSmallest(root.left, k);
        if (k[0] == 0)
            return val;
        if (--k[0] == 0)
            return root.val;
        return kthSmallest(root.right, k);
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        if (root == p || root == q)
            return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null)
            return root;
        return left != null ? left : right;
    }


    public static TreeNode reverse(TreeNode root) {
        if (root == null || root.left == null)
            return root;

        TreeNode temp = root.left;
        TreeNode newRoot = reverse(root.left);
        temp.left = root.right;
        temp.right = root;
        root.left = null;
        root.right = null;
        return newRoot;
    }


    public static void main(String[] args) {
//        int[] nums = new int[]{3, 2, 1, 5, 6, 4};
//        System.out.println(new Algorithms().findKthLargest(nums, 2));
//        System.out.println(Arrays.toString(nums));
        print();
    }


    public static void print() {
        int l1 = 10, l2 = 225, l3 = 7, l4 = 254,
                h1 = 10, h2 = 225, h3 = 8, h4 = 3;
        while (true) {
            System.out.println(String.format("%d %d %d %d", l1, l2, l3, l4));
            if (l1 >= h1 && l2 >= h2 && l3 >= h3 && l4 >= h4)
                break;

            l4++;
            if (l4 == 256) {
                l4 = 0;
                l3++;
                if (l3 == 256) {
                    l3 = 0;
                    l2++;
                    if (l2 == 256) {
                        l2 = 0;
                        l1++;
                    }
                }
            }
        }
    }

    /**
     * 删除二叉搜索树中的指定节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            TreeNode node = root.right;
            while (node.left != null) {
                node = node.left;
            }
            root.right = deleteNode(root.right, node.val);
            node.left = root.left;
            node.right = root.right;
            return node;
        }
        return root;
    }

    /**
     * 207. 课程表
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] arr : prerequisites) {
            inDegree[arr[0]]++;
            if (!map.containsKey(arr[1])) {
                map.put(arr[1], new ArrayList<>());
            }
            map.get(arr[1]).add(arr[0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            List<Integer> postCourses = map.get(course);
            if (postCourses != null) {
                for (int postCourse : map.get(course)) {
                    if (--inDegree[postCourse] == 0) {
                        queue.add(postCourse);
                    }
                }
            }
        }
        return count == numCourses;
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;

        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        boolean leftToRight = true;
        while (!stack.isEmpty()) {
            List<Integer> levelValues = new ArrayList<>();
            Stack<TreeNode> levelStack = new Stack<>();
            for (int i = stack.size(); i > 0; i--) {
                TreeNode node = stack.pop();
                levelValues.add(node.val);
                if (leftToRight) {
                    if (node.left != null) levelStack.add(node.left);
                    if (node.right != null) levelStack.add(node.right);
                } else {
                    if (node.right != null) levelStack.add(node.right);
                    if (node.left != null) levelStack.add(node.left);
                }
            }
            res.add(levelValues);
            stack = levelStack;
            leftToRight = !leftToRight;
        }
        return res;
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        List<int[]> list = new ArrayList<>();
        int start = -1, end = -1;
        for (int[] arr : intervals) {
            if (arr[0] > end) {
                if (end != -1) {
                    list.add(new int[]{start, end});
                }
                start = arr[0];
            }
            end = Math.max(end, arr[1]);
        }
        list.add(new int[]{start, end});

        int[][] res = new int[list.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }


}