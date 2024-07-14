import java.util.*;

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

    public void dfs(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            curr = curr.right;
        }
    }

    /**
     * 144. 二叉树的前序遍历 根左右
     * 94. 二叉树的中序遍历 左根右
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                list.add(curr.val);
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
//            list.add(curr.val);   // inorder traversal
            curr = curr.right;
        }
        return list;
    }

    /**
     * 145. 二叉树的后序遍历 左右根
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root, prev = null;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (curr.right == null || curr.right == prev) {
                list.add(curr.val);
                prev = curr;
                curr = null;
            } else {
                stack.add(curr);
                curr = curr.right;
            }
        }
        return list;
    }

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>(nums1.length);
        for (int num : nums1) {
            set1.add(num);
        }
        Set<Integer> set2 = new HashSet<>(nums2.length);
        for (int num : nums2) {
            set2.add(num);
        }

        Set<Integer> commonSet = new HashSet<>(set1);
        commonSet.retainAll(set2);

        set1.removeAll(commonSet);
        set2.removeAll(commonSet);

        return Arrays.asList(new ArrayList<>(set1), new ArrayList<>(set2));
    }

    /**
     * 1 <= arr.length <= 1000
     * -1000 <= arr[i] <= 1000
     */
    public boolean uniqueOccurrences(int[] arr) {
        int n = arr.length;

        int[] cnt = new int[2001];
        for (int d : arr) {
            cnt[d + 1000]++;
        }

        boolean[] times = new boolean[50];
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] != 0) {
                if (times[cnt[i]]) {
                    return false;
                } else {
                    times[cnt[i]] = true;
                }
            }
        }
        return true;
    }

    /**
     * 1 <= word1.length, word2.length <= 10^5
     */
    public static boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int n = word1.length();
        int[] cnt1 = new int[26];
        for (int i = 0; i < n; i++) {
            cnt1[word1.charAt(i) - 'a']++;
        }
        int[] cnt2 = new int[26];
        for (int i = 0; i < n; i++) {
            cnt2[word2.charAt(i) - 'a']++;
        }

        // 字符种类必须相同
        for (int i = 0; i < 26; i++) {
            if (cnt1[i] == 0 && cnt2[i] == 0) {
                continue;
            }
            if (cnt1[i] == 0 || cnt2[i] == 0) {
                return false;
            }
        }

        Arrays.sort(cnt1);
        Arrays.sort(cnt2);

        for (int i = 0; i < 26; i++) {
            if (cnt1[i] != cnt2[i]) {
                return false;
            }
        }
        return true;
    }

    public String removeStars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public int[] asteroidCollision(int[] asteroids) {
        List<Integer> list = new ArrayList<>();
        int i = 0, n = asteroids.length;
        while (i < n) {
            int a = asteroids[i];
            if (list.size() > 0 && list.get(list.size() - 1) > 0 && a < 0) {
                int left = list.get(list.size() - 1), right = -a;
                if (left > right) {
                    i++;
                } else if (left < right) {
                    list.remove(list.size() - 1);
                } else {
                    list.remove(list.size() - 1);
                    i++;
                }
            } else {
                list.add(a);
                i++;
            }
        }

        int[] res = new int[list.size()];
        for (int j = 0; j < list.size(); j++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public static String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        int i = 0, n = s.length();
        while (i < n) {
            if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                numStack.push(num);
            } else if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                StringBuilder sb = new StringBuilder();
                while (i < n && s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                    sb.append(s.charAt(i));
                    i++;
                }
                strStack.push(sb.toString());
            } else if (s.charAt(i) == '[') {
                strStack.push("[");
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                String str = strStack.pop();
                do {
                    sb.insert(0, str);
                    str = strStack.pop();
                } while (!"[".equals(str));
                str = sb.toString();
                for (int j = numStack.pop() - 1; j > 0; j--) {
                    sb.append(str);
                }
                strStack.push(sb.toString());
                i++;
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!strStack.isEmpty()) {
            sb.insert(0, strStack.pop());
        }
        return sb.toString();
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            ans.add(queue.peek().val);
            TreeNode node;
            for (int i = queue.size(); i > 0; i--) {
                node = queue.remove();
                if (node.right != null) {
                    queue.add(node.right);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
            }
        }
        return ans;
    }

    public int maxLevelSum(TreeNode root) {
        long sum = Long.MIN_VALUE;
        int level = -1;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            depth++;
            long levelSum = 0;
            TreeNode node;
            for (int i = queue.size(); i > 0; i--) {
                node = queue.remove();
                levelSum += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            if (levelSum > sum) {
                sum = levelSum;
                level = depth;
            }
        }
        return level;
    }

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] visited = new boolean[rooms.size()];
        dfs(rooms, 0, visited);
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    private void dfs(List<List<Integer>> rooms, int idx, boolean[] visited) {
        if (visited[idx]) {
            return;
        }
        visited[idx] = true;
        for (int i : rooms.get(idx)) {
            dfs(rooms, i, visited);
        }
    }

    public int minReorder(int n, int[][] connections) {
        List<int[]>[] e = new List[n];
        for (int i = 0; i < n; i++) {
            e[i] = new ArrayList<>();
        }
        for (int[] edge : connections) {
            e[edge[0]].add(new int[]{edge[1], 1});
            e[edge[1]].add(new int[]{edge[0], 0});
        }

        Set<Integer> visited = new HashSet<>();
        return dfs(e, 0, visited);
    }

    private int dfs(List<int[]>[] e, int idx, Set<Integer> visited) {
        visited.add(idx);
        int sum = 0;
        for (int[] edge : e[idx]) {
            if (!visited.contains(edge[0])) {
                sum += edge[1] + dfs(e, edge[0], visited);
            }
        }
        return sum;
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<Item>> map = new HashMap<>();
        int m = equations.size();
        List<String> list;
        for (int i = 0; i < m; i++) {
            list = equations.get(i);
            if (!map.containsKey(list.get(0))) {
                map.put(list.get(0), new ArrayList<>());
            }
            if (!map.containsKey(list.get(1))) {
                map.put(list.get(1), new ArrayList<>());
            }
            map.get(list.get(0)).add(new Item(list.get(1), values[i]));
            map.get(list.get(1)).add(new Item(list.get(0), 1 / values[i]));
        }

        int n = queries.size();
        double[] ans = new double[n];
        for (int i = 0; i < n; i++) {
            list = queries.get(i);
            if (!map.containsKey(list.get(0)) || !map.containsKey(list.get(1))) {
                ans[i] = -1.0;
            } else {
                ans[i] = dfs(map, list.get(0), list.get(1), new HashSet<>());
            }
        }
        return ans;
    }

    private double dfs(Map<String, List<Item>> map, String from, String to, Set<String> visited) {
        if (Objects.equals(from, to)) {
            return 1.0;
        }
        if (map.containsKey(from)) {
            for (Item item : map.get(from)) {
                if (!visited.contains(item.e)) {
                    visited.add(item.e);
                    double val = dfs(map, item.e, to, visited);
                    if (val != -1.0) {
                        return item.val * val;
                    }
                    visited.remove(item.e);
                }
            }
        }
        return -1.0;
    }

    class Item {
        String e;
        double val;

        public Item(String ee, double v) {
            e = ee;
            val = v;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "e='" + e + '\'' +
                    ", val=" + val +
                    '}';
        }
    }

    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int m = spells.length, n = potions.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int lo = 0, hi = n, mid;
            while (lo < hi) {
                mid = lo + ((hi - lo) >>> 1);
                if ((long) spells[i] * mid >= success) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            ans[i] = n - hi;
        }
        return ans;
    }

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(o -> o[1]));
        int i = 0, n = points.length, ans = 0;
        while (i < n) {
            ans++;
            int j = points[i++][1];
            while (i < n && points[i][0] < j) {
                i++;
            }
        }
        return ans;
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        TrieNode root = new TrieNode('0');
        for (String p : products) {
            buildTrie(root, p);
        }

        int i = 0, n = searchWord.length();
        List<List<String>> ans = new ArrayList<>(n);
        TrieNode curr = root;
        char c;
        StringBuilder sb = new StringBuilder();
        while (i < n) {
            c = searchWord.charAt(i);
            if (curr.child.get(c) == null) {
                ans.add(new ArrayList<>(0));
            } else {
                curr = curr.child.get(c);
                List<String> list = new ArrayList<>(3);
                dfs(curr, list, sb);
                ans.add(list);
                sb.append(c);
            }
            i++;
        }
        return ans;
    }

    private void dfs(TrieNode curr, List<String> list, StringBuilder sb) {
        if (list.size() == 3) {
            return;
        }
        sb.append(curr.c);
        if (curr.end) {
            list.add(sb.toString());
        }
        for (TrieNode trieNode : curr.child.values()) {
            dfs(trieNode, list, sb);
        }
        sb.deleteCharAt(sb.length() - 1);
    }

    private void buildTrie(TrieNode root, String p) {
        int i = 0, n = p.length();
        char c;
        while (i < n) {
            c = p.charAt(i);
            if (root.child.get(c) == null) {
                root.child.put(c, new TrieNode(c));
            }
            root = root.child.get(c);
            i++;
        }
        root.end = true;
    }

    class TrieNode {
        char c;
        boolean end = false;
        Map<Character, TrieNode> child = new TreeMap<>();

        public TrieNode(char ch) {
            c = ch;
        }
    }

    public int longestCommonSubsequenceNormal(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i + 1][j + 1] = Math.max(dp[i][j] + 1, Math.max(dp[i + 1][j], dp[i][j + 1]));
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[m][n];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[] dp1 = new int[n + 1];
        int[] dp2 = new int[n + 1];
        int[] temp;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp2[j + 1] = Math.max(dp1[j] + 1, Math.max(dp2[j], dp1[j + 1]));
                } else {
                    dp2[j + 1] = Math.max(dp2[j], dp1[j + 1]);
                }
            }
            temp = dp1;
            dp1 = dp2;
            dp2 = temp;
        }
        return dp1[n];
    }

    public int maxProfitNormal(int[] prices, int fee) {
        int n = prices.length;
        int ans = 0;
        int[][] dp = new int[2][n]; // 0-hold, 1-not
        dp[0][0] = -prices[0];
        dp[1][0] = 0;
        for (int i = 1; i < n; i++) {
            dp[0][i] = Math.max(dp[0][i - 1], dp[1][i - 1] - prices[i]);
            dp[1][i] = Math.max(dp[1][i - 1], dp[0][i - 1] + prices[i] - fee);
            ans = Math.max(ans, Math.max(dp[0][i], dp[1][i]));
        }
        return ans;
    }

    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int ans = 0;
        int hold = -prices[0], empty = 0, temp;
        for (int i = 1; i < n; i++) {
            temp = Math.max(hold, empty - prices[i]);
            empty = Math.max(empty, hold + prices[i] - fee);
            hold = temp;
            ans = Math.max(ans, Math.max(hold, empty));
        }
        return ans;
    }

    public int nearestExit(char[][] maze, int[] entrance) {
        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(entrance[0], entrance[1]));
        maze[entrance[0]][entrance[1]] = 'x';
        int step = 0;
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        Cell cell;
        int i, j, m = maze.length, n = maze[0].length;
        while (!queue.isEmpty()) {
            step++;
            for (int k = queue.size(); k > 0; k--) {
                cell = queue.remove();
                for (int[] dir : dirs) {
                    i = cell.i + dir[0];
                    j = cell.j + dir[1];
                    if (i < 0 || i == m || j < 0 || j == n) {
                        continue;
                    }
                    if (maze[i][j] == '.') {
                        if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                            return step;
                        } else {
                            queue.add(new Cell(i, j));
                            maze[i][j] = 'x';
                        }
                    }
                }
            }
        }
        return -1;
    }

    class Cell {
        int i, j;

        public Cell(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    private static final int BAD = 2;
    private static final int GOOD = 1;
    private static final int[][] DIRS = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    public int orangesRotting(int[][] grid) {
        List<Cell> list = new ArrayList<>();
        int m = grid.length, n = grid[0].length;
        // init bad oranges
        int orangeCnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == BAD || grid[i][j] == GOOD) {
                    orangeCnt++;
                }
                if (grid[i][j] == BAD) {
                    list.add(new Cell(i, j));
                }
            }
        }
        // fast return
        if (orangeCnt == list.size()) {
            return 0;
        }
        // bfs
        Cell cell;
        int minutes = 0, i, j;
        while (orangeCnt != list.size()) {
            boolean changed = false;
            minutes++;
            for (int k = list.size() - 1; k >= 0; k--) {
                cell = list.get(k);
                for (int[] dir : DIRS) {
                    i = cell.i + dir[0];
                    j = cell.j + dir[1];
                    if (i < 0 || i == m || j < 0 || j == n) {
                        continue;
                    }
                    if (grid[i][j] == GOOD) {
                        list.add(new Cell(i, j));
                        grid[i][j] = BAD;
                        changed = true;
                    }
                }
            }
            if (!changed) {
                return -1;
            }
        }
        return minutes;
    }

    public long totalCost(int[] costs, int k, int candidates) {
        // init 2 min-heaps
        PriorityQueue<Integer> queue1 = new PriorityQueue<>(), queue2 = new PriorityQueue<>();
        int left = candidates - 1, n = costs.length, right = Math.max(candidates, n - candidates);
        for (int i = 0; i <= left; i++) {
            queue1.add(costs[i]);
        }
        for (int i = right; i < n; i++) {
            queue2.add(costs[i]);
        }

        // k loop
        long cost = 0;
        while (k-- > 0) {
            if (!queue1.isEmpty() && (queue2.isEmpty() || queue1.peek() <= queue2.peek())) {
                cost += queue1.remove();
                if (left + 1 < right) {
                    queue1.add(costs[++left]);
                }
            } else {
                cost += queue2.remove();
                if (left < right - 1) {
                    queue2.add(costs[--right]);
                }
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        System.out.println(decodeString("2[abc]3[cd]ef"));
        new TreeAlgorithms().longestCommonSubsequence("ezupkr", "ubmrapg");
    }
}
