import java.util.*;

public class Test4 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> roots = new ArrayList<>();
        Set<Integer> toDeleteSet = new HashSet<>(to_delete.length);
        for (int val : to_delete) {
            toDeleteSet.add(val);
        }
        delNodes(root, true, toDeleteSet, roots);
        return roots;
    }


    public TreeNode delNodes(TreeNode root, boolean isRoot, Set<Integer> toDeleteSet, List<TreeNode> roots) {
        if (root == null) {
            return null;
        }

        boolean childIsRoot = toDeleteSet.contains(root.val);
        root.left = delNodes(root.left, childIsRoot, toDeleteSet, roots);
        root.right = delNodes(root.right, childIsRoot, toDeleteSet, roots);

        if (toDeleteSet.contains(root.val)) {
            return null;
        } else {
            if (isRoot) {
                roots.add(root);
            }
            return root;
        }
    }

    public int minCostConnectPoints(int[][] points) {
        int ans = 0;
        int n = points.length;
        int[] distance = new int[n];
        distance[0] = 0;    // 0 表示这个点已加入最小生成树
        for (int j = 1; j < n; j++) {
            distance[j] = calDistance(points, 0, j);
        }
        for (int i = 1; i < n; i++) {
            // 找到最近的点，加入pointSet
            int minDistance = Integer.MAX_VALUE;
            int minPointIdx = 0;
            for (int j = 0; j < n; j++) {
                if (distance[j] != 0 && distance[j] < minDistance) {
                    minDistance = distance[j];
                    minPointIdx = j;
                }
            }
            ans += minDistance;
            // 更新其他点到pointSet的距离
            distance[minPointIdx] = 0;
            for (int j = 0; j < n; j++) {
                if (distance[j] != 0) {
                    distance[j] = Math.min(distance[j], calDistance(points, minPointIdx, j));
                }
            }
        }
        return ans;
    }

    private int calDistance(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }

    class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "{(" + x + "," + y + ")}";
        }
    }

    class Status {
        private Point point;
        private Status pre;
        private int step;

        public Status(Point point, Status pre, int step) {
            this.point = point;
            this.pre = pre;
            this.step = step;
        }
    }

    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    // 判断迷宫走出的最短移动步数，并给出移动路径，BFS
    public List<Point> fun(int[][] maze) {
        int m = maze.length, n = maze[0].length;
        Deque<Status> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.addLast(new Status(new Point(0, 0), null, 0));
        visited.add(0);

        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                Status status = queue.removeFirst();
                for (int[] dir : dirs) {
                    int newX = status.point.x + dir[0], newY = status.point.y + dir[1];
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && maze[newX][newY] == 0 && !visited.contains(newX * m + newY)) {
                        queue.addLast(new Status(new Point(newX, newY), status, status.step + 1));
                        visited.add(newX * m + newY);
                        if (newX == m - 1 && newY == n - 1) {
                            List<Point> points = new ArrayList<>();
                            Status curr = queue.peekLast();
                            while (curr != null) {
                                points.add(curr.point);
                                curr = curr.pre;
                            }
                            Collections.reverse(points);
                            return points;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static int countFamilyPairs(String[] logics) {
        Map<String, Integer> stringOccurrences = new HashMap<>();
        int pairCount = 0;

        // 记录每个字符串出现的次数
        for (String s : logics) {
            stringOccurrences.put(s, stringOccurrences.getOrDefault(s, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : stringOccurrences.entrySet()) {
            String t = getNextString(entry.getKey());
            if (stringOccurrences.containsKey(t) && !entry.getKey().equals(t)) {
                pairCount += entry.getValue() * stringOccurrences.get(t);
            }
        }

        return pairCount;
    }

    // 计算字符串s的"下一个"字符串，即每个字符向后移动一位
    private static String getNextString(String s) {
        StringBuilder nextS = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == 'z') {
                nextS.append('a');  // 特殊处理z->a
            } else {
                nextS.append((char)(c + 1));
            }
        }

        return nextS.toString();
    }

    public int largestVariance(String s) {
        int ans = 0;
        for (var a = 'a'; a <= 'b'; ++a)
            for (var b = 'a'; b <= 'b'; ++b) {
                if (a == b) {
                    continue;
                }
                int diff = 0, diffWithB = Integer.MIN_VALUE;
                for (var i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == a) {
                        diff++;
                        diffWithB++;
                    } else if (s.charAt(i) == b) {
                        diff--;
                        diffWithB = diff;
                        diff = Math.max(0, diff);
                    }
                    System.out.println(diff + "-" +diffWithB);
                    ans = Math.max(ans, diffWithB);
                }
            }
        return ans;
    }

    public static int maxSegmentSplit(int[] nums) {
        int n = nums.length;
        if (n < 2) return 0; // 处理边界情况

        // 初始化优先队列，存储元素值和索引，使用自定义比较器实现降序排列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> -a[0]));
        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{nums[i], i});
        }

        Set<Integer> splitPoints = new HashSet<>(); // 已经作为分割点的索引集合
        int segments = 1; // 分割次数

        while (!pq.isEmpty()) {
            int[] top = pq.poll(); // 弹出最大值及其索引
            int val = top[0], index = top[1];

            // 检查后一位是否可以作为分割点
            int nextIndex = index + 1;
            if (!splitPoints.contains(index) && nextIndex < n && !splitPoints.contains(nextIndex) && nextIndex + 1 < n && nums[index] > nums[nextIndex]) {
                splitPoints.add(nextIndex); // 标记为分割点
                segments++; // 分割次数加一
            }
        }

        return segments;
    }

    public static void main(String[] args) {
        Test4 test = new Test4();
//        System.out.println(test.fun(new int[][]{
//                {0, 1, 0},
//                {0, 0, 1},
//                {1, 0, 0}
//        }));

        Random random = new Random(618);
        for (int i = 0; i < 31; i++) {
            System.out.println("换手机：" + random.nextBoolean());
        }

    }
}
