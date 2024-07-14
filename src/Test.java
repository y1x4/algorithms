import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class Test {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, maxAreaOfIsland(grid, i, j));
                }
            }
        }
        return maxArea;
    }

    private int maxAreaOfIsland(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
            return 0;
        }

        grid[i][j] = 2;

        return 1 + maxAreaOfIsland(grid, i + 1, j)
                + maxAreaOfIsland(grid, i - 1, j)
                + maxAreaOfIsland(grid, i, j + 1)
                + maxAreaOfIsland(grid, i, j - 1);
    }

    static class RunThread implements Callable {
        String name;
        CyclicBarrier cyclicBarrier;
        public RunThread(String name, CyclicBarrier c) {
            this.name = name;
            this.cyclicBarrier = c;
        }

        @Override
        public Object call() throws Exception {
            System.out.println(name + " is ready");
            cyclicBarrier.await();
            System.out.println(name + " is running");
            return new Random().nextInt(10);
        }
    }



    public static void main(String[] args) throws Exception {
        Test test = new Test();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        Object threadA = new RunThread("A", cyclicBarrier).call();
        Object threadB = new RunThread("B", cyclicBarrier).call();
        Object threadC = new RunThread("C", cyclicBarrier).call();

    }
}

