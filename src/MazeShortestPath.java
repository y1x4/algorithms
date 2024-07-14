import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeShortestPath {

    int minStep = Integer.MAX_VALUE;
    List<int[]> shortestPath = null;
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public void dfs(int[][] maze) {
        dfs(maze, 0, 0, new ArrayList<>());
        if (shortestPath != null) {
            for (int[] p : shortestPath) {
                System.out.printf("(%d,%d)%n", p[0], p[1]);
            }
        }
    }


    private void dfs(int[][] maze, int i, int j, List<int[]> path) {
        // 不合法条件与剪枝
        if (!valid(maze, i, j) || path.size() > minStep) {
            return;
        }

        // 状态转移
        maze[i][j] = 2;
        path.add(new int[]{i, j});

        // 终止条件
        if (i == maze.length - 1 && j == maze[0].length - 1) {
            if (path.size() < minStep) {
                minStep = path.size();
                shortestPath = new ArrayList<>(path);
            }
        } else {
            // 递归调用
            for (int[] dir : dirs) {
                dfs(maze, i + dir[0], j + dir[1], path);
            }
        }

        // 状态还原
        maze[i][j] = 0;
        path.remove(path.size() - 1);
    }

    private boolean valid(int[][] maze, int i, int j) {
        return i >= 0 && i < maze.length && j >= 0 && j < maze[0].length && maze[i][j] == 0;
    }

    static class Point {
        Point prev;
        int i, j;
        public Point(int i, int j, Point prev) {
            this.i = i;
            this.j = j;
            this.prev = prev;
        }
    }

    public void bfs(int[][] maze) {
        int m = maze.length, n = maze[0].length;
        Queue<Point> queue = new LinkedList<>();
        // 初始状态
        queue.add(new Point(0, 0, null));

        Point point;
        while (!queue.isEmpty()) {
            // 多轮遍历
            for (int i = queue.size() - 1; i >= 0; i--) {
                point = queue.remove();
                if (point.i == m - 1 && point.j == n - 1) {
                    print(point);
                    return;
                } else {
                    for (int[] dir : dirs) {
                        int x = point.i + dir[0], y = point.j + dir[1];
                        if (valid(maze, x, y)) {
                            queue.add(new Point(x, y, point));
                        }
                    }
                }
            }
        }
    }

    private void print(Point point) {
        if (point.prev != null) {
            print(point.prev);
        }
        System.out.printf("(%d,%d)%n", point.i, point.j);
    }


    public static void main(String[] args) {
        MazeShortestPath test = new MazeShortestPath();
        int[][] maze = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0}
        };
        test.dfs(maze);
        test.bfs(maze);
    }
}
