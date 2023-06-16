import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class CourseSchedule {

    /**
     * 207.课程表I
     * 等价于求图中有没有环，没有环就能完成，0 1 2 表示未访问、访问中、已完成，可dfs、bfs，dfs简单点
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 课和以它为前置的课
        Map<Integer, List<Integer>> preAndAfterCourses = new HashMap<>(numCourses);
        // 课和它的前置课
        Map<Integer, List<Integer>> afterAndPreCourses = new HashMap<>(numCourses);
        for (int[] pre : prerequisites) {
            if (!preAndAfterCourses.containsKey(pre[1])) {
                preAndAfterCourses.put(pre[1], new ArrayList<>());
            }
            preAndAfterCourses.get(pre[1]).add(pre[0]);

            if (!afterAndPreCourses.containsKey(pre[0])) {
                afterAndPreCourses.put(pre[0], new ArrayList<>());
            }
            afterAndPreCourses.get(pre[0]).add(pre[1]);
        }

        int numCompleted = 0;
        boolean[] completed = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            numCompleted += takeCourses(preAndAfterCourses, afterAndPreCourses, completed, i);
        }
        return numCompleted == numCourses;
    }

    private int takeCourses(Map<Integer, List<Integer>> preAndAfterCourses, Map<Integer, List<Integer>> afterAndPreCourses, boolean[] completed, int i) {
        List<Integer> preCourses = afterAndPreCourses.get(i);
        if (!completed[i] && (preCourses == null || preCourses.isEmpty())) {
            // 如果没有前置课，上课+1
            int cnt = 1;
            completed[i] = true;
            List<Integer> afterCourses = preAndAfterCourses.get(i);
            if (afterCourses != null) {
                // 以它为前置的课不为空，尝试继续上
                for (int c : afterCourses) {
                    // 先从前置课中移除掉i
                    afterAndPreCourses.get(c).remove(Integer.valueOf(i));
                    cnt += takeCourses(preAndAfterCourses, afterAndPreCourses, completed, c);
                }
                preAndAfterCourses.remove(i);
            }
            return cnt;
        }
        return 0;
    }

    /**
     * 210.课程表II
     * 拓扑排序，返回任意一种完成的顺序，不能完成返回空数组
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // node and edges
        Map<Integer, List<Integer>> preAndAfterCourses = new HashMap<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            preAndAfterCourses.put(i, new ArrayList<>());
        }
        for (int[] pre : prerequisites) {
            preAndAfterCourses.get(pre[1]).add(pre[0]);
        }

        List<Integer> res = new ArrayList<>();
        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (!findOrder(preAndAfterCourses, res, visited, i)) {
                    return new int[]{};
                }
            }
        }
        Collections.reverse(res);
        return res.stream().mapToInt(i -> i).toArray();
    }

    private boolean findOrder(Map<Integer, List<Integer>> preAndAfterCourses, List<Integer> res, int[] visited, int i) {
        if (visited[i] == 1) {
            return false;
        }
        if (visited[i] == 2) {
            return true;
        }

        visited[i] = 1;
        for (int next : preAndAfterCourses.get(i)) {
            if (!findOrder(preAndAfterCourses, res, visited, next)) {
                return false;
            }
        }
        visited[i] = 2;
        res.add(i);
        return true;
    }

    /**
     * 630.课程表III
     * courses[i] = [durationi, lastDayi]，返回最多可以修读的课程数目
     */
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, Comparator.comparingInt(c -> c[1]));

        PriorityQueue<Integer> courseDurations = new PriorityQueue<>((a, b) -> b - a);
        int total = 0, duration, lastDay;
        for (int[] course : courses) {
            duration = course[0];
            lastDay = course[1];
            if (total + duration < lastDay) {
                total += duration;
                courseDurations.add(duration);
            } else if (courseDurations.size() > 0 && courseDurations.peek() > duration) {
                total = total - courseDurations.remove() + duration;
                courseDurations.add(duration);
            }
        }
        return courseDurations.size();
    }

    /**
     * 1462.课程表IV
     * pre[1]是pre[0]前置课程，给一串query，返回q[0]是否是q[1]前置课程
     */
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 1.build node and edges
        Map<Integer, List<Integer>> preAndAfterCourses = new HashMap<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            preAndAfterCourses.put(i, new ArrayList<>());
        }
        for (int[] pre : prerequisites) {
            preAndAfterCourses.get(pre[1]).add(pre[0]);
        }

        // 2.dfs and cache
        Map<Integer, Set<Integer>> cache = new HashMap<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            dfs(preAndAfterCourses, cache, i);
        }

        // 3.queries
        List<Boolean> result = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            result.add(cache.get(query[1]).contains(query[0]));
        }
        return result;
    }

    private Set<Integer> dfs(Map<Integer, List<Integer>> preAndAfterCourses, Map<Integer, Set<Integer>> cache, Integer i) {
        if (cache.containsKey(i)) {
            return cache.get(i);
        }

        Set<Integer> result = new HashSet<>();
        List<Integer> afterCourses = preAndAfterCourses.get(i);
        if (!afterCourses.isEmpty()) {
            result.addAll(afterCourses);
            for (Integer c : afterCourses) {
                result.addAll(dfs(preAndAfterCourses, cache, c));
            }
        }
        cache.put(i, result);
        return result;
    }


    /**
     * 1136.并行课程
     * 拓扑排序有多少层
     */
    public int minimumSemesters(int n, int[][] relations) {
        // 1.build node and edges, inDegree arr
        Map<Integer, List<Integer>> preAndAfterCourses = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            preAndAfterCourses.put(i, new ArrayList<>());
        }
        int[] inDegree = new int[n + 1];
        for (int[] arr : relations) {
            preAndAfterCourses.get(arr[0]).add(arr[1]);
            inDegree[arr[1]]++;
        }

        // 3.
        int result = 0, numStudied = 0;
        while (numStudied < n) {
            boolean flag = false;
            for (int i = 1; i <= n; i++) {
                if (inDegree[i] == 0) {
                    inDegree[i] = -1;
                    flag = true;
                    numStudied++;
                    for (int next : preAndAfterCourses.get(i)) {
                        inDegree[next]--;
                    }
                }
            }
            if (!flag) {
                return -1;
            }
            result++;
        }
        return result;
    }


    public static void main(String[] args) {
        CourseSchedule test = new CourseSchedule();
//        System.out.println(test.canFinish(2, new int[][]{{0, 1}}));
        int[] res = test.findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}});
        System.out.println(Arrays.toString(res));
    }
}
