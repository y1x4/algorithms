import java.util.Arrays;

/**
 * @author yixu
 */
public class DynamicProgramming {


    // 322
    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int c : coins) {
                if (i - c >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - c] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }


    // 300 longest increasing subsequence
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLength = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    maxLength = Math.max(maxLength, dp[i]);
                }
            }
        }
        return maxLength;
    }


    public int lengthOfLIS(int[] nums, int nlogn) {
        int n = nums.length;
        int[] pileTops = new int[n];

        int numOfPiles = 0;
        for (int d : nums) {
            int left = 0, right = numOfPiles, mid;
            while (left < right) {
                mid = left + ((right - left) >> 1);
                if (d < pileTops[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            if (left == numOfPiles) {
                numOfPiles++;
            }
            pileTops[left] = d;
        }

        System.out.print(Arrays.toString(pileTops));
        return numOfPiles;
    }


    // 0-1 背包
    public int maxVal(int[] values, int[] weights, int maxWeight) {
        int n = values.length;
        int[][] dp = new int[n + 1][maxWeight + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= maxWeight; w++) {
                if (w >= weights[i]) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][maxWeight];
    }


    public int minDistance(String s1, String s2) {
        assert s1 != null && s2 != null;

        int m = s1.length(), n = s2.length();
        if (m * n == 0) {
            return m + n;
        }
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // 跳过
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 替换、删除（同插入）
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }


    // 887 K and N > 0
    // dp[k][f] = dp[k][f-1] + dp[k-1][f-1] + 1
    public int superEggDrop(int K, int N) {
        if (K == 1) return N;

        int[] dp = new int[K + 1];
        int result = 0;
        while (dp[K] < N) {
            for (int i = K; i > 0; i--) {
                dp[i] = dp[i] + dp[i - 1] + 1;
            }
            result++;
        }
        return result;
    }



    public int longestCommonSubsequence(String s1, String s2) {
        assert s1 != null && s2 != null;
        int m = s1.length(), n = s2.length();
        if (m * n == 0) {
            return 0;
        }

        int[] dp = new int[n + 1];
        int up;
        for (int i = 1; i <= m; i++) {
            int leftUp = 0;
            for (int j = 1; j <= n; j++) {
                up = dp[j];
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[j] = leftUp + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                leftUp = up;
            }
        }
        return dp[n];
    }


    // 516
    public int longestPalindromeSubseq(String s) {
        int n = s.length();

        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }
        return dp[0][n - 1];
    }


    class Pair{
        int first, second;
        Pair(int f, int s) {
            first = f;
            second = s;
        }
        public int getDifference() {
            return first - second;
        }
    }

    public int stoneGame(int[] piles) {
        int n = piles.length;
        Pair[][] dp = new Pair[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = new Pair(piles[i], 0);
        }

        for (int i = n - 1; i >= 0; i++) {
            for (int j = i + 1; j < n; j++) {
                int left = piles[i] + dp[i + 1][j].second;
                int right = piles[j] + dp[i][j + 1].second;
                if (left > right) {
                    dp[i][j] = new Pair(left, dp[i + 1][j].first);
                } else {
                    dp[i][j] = new Pair(right, dp[i][j + 1].first);
                }
            }
        }
        return dp[0][n - 1].getDifference();
    }


    // 10
    public boolean isMatch(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int i = text.length(); i >= 0; i--){
            for (int j = pattern.length() - 1; j >= 0; j--){
                boolean currentMatch = i < text.length() &&
                        (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.');
                if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*') {
                    // * 取 0 或 1
                    dp[i][j] = dp[i][j+2] || (currentMatch && dp[i+1][j]);
                } else {
                    dp[i][j] = currentMatch && dp[i+1][j+1];
                }
            }
        }
        return dp[0][0];
    }


    static class Thing {
        int w, v;
        public Thing(int weight, int value) {
            w = weight;
            v = value;
        }
    }
    /**
     * 0-1 背包：dp[i][j] = max(dp[i-1][j], dp[i-1][j-w[i]] + v[i])
     */
    public int backpack1(Thing[] list, int W) {
        int[] dp = new int[W+1];
        int wi, vi;
        for (int i = 1; i <= list.length; i++) {
            wi = list[i-1].w;
            vi = list[i-1].v;
            // 必须逆向
            for (int j = W; j >= wi; j--) {
                dp[j] = Math.max(dp[j], dp[j - wi] + vi);
            }
        }
        return dp[W];
    }

    /**
     * 完全背包：dp[i][j] = max(dp[i-1][j], dp[i][j-w[i]] + v[i])
     * 第i（i从1开始）种物品的数量为n[i]
     * */
    public int backpack2(Thing[] list, int W) {
        int[] dp = new int[W+1];
        int wi, vi;
        for (int i = 1; i <= list.length; i++) {
            wi = list[i-1].w;
            vi = list[i-1].v;
            // 必须正向
            for (int j = wi; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - wi] + vi);
            }
        }
        return dp[W];
    }

    /**
     * 多重背包：dp[i][j] = max{(dp[i-1][j − k*w[i]] + k*v[i]) for every k}, k <= min(n[i], j/w[i])
     *      第i种物品的数量为n[i]
     *
     * */
    public int backpack3(Thing[] list, int[] amounts, int W) {
        int[] dp = new int[W+1];
        int wi, vi;
        for (int i = 1; i <= list.length; i++) {
            wi = list[i-1].w;
            vi = list[i-1].v;
            // 必须逆向
            for (int j = W; j >= wi; j--) {
                for (int k = Math.min(amounts[i], j / wi); k >= 0; k--) {
                    dp[j] = Math.max(dp[j], dp[j - k*wi] + k*vi);
                }
            }
        }
        return dp[W];
    }


    /**
     * LeetCode 300. Longest Increasing Subsequence 最长上升子串
     * 解法一：动态规划
     * 解法二：维护一个上升子串，二分法进行替换（firstGE）或尾部增添
     * */



    // 部分和为 k 的不同方案数
    public int partSum(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[k+1];
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            for (int j = k; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[k];
    }


    /**
     * 求数组部分和对 m 取余的最大值
     *
     * */
    public int partSum2(int[] nums, int m) {
        int n = nums.length;
        boolean[] dp = new boolean[m];
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            for (int j = m-1; j >= 0; j--) {
                int index = getIndex(j - nums[i], m);
//                if (dp[index]) {
//                    dp[j] = true;
//                }
                dp[j] |= dp[index];
            }
        }

        for (int j = m-1; j >= 0; j--) {
            if (dp[j])
                return j;
        }
        return 0;
    }
    private int getIndex(int k, int m) {
        int res = k % m;
        if (res < 0)
            res += m;
        return res;
    }


    public static void main(String[] args) {
        System.out.println(new DynamicProgramming().partSum(new int[]{5, 5, 10, 2, 3}, 15));   // 4
        System.out.println(new DynamicProgramming().partSum2(new int[]{2, 4, 10, 10}, 4));   // 2
        System.out.println(new DynamicProgramming().partSum2(new int[]{2, 4, 10, 10}, 28));  // 26
        System.out.println(new DynamicProgramming().partSum2(new int[]{2, 7, 5, 9, 3}, 11)); // 10
//        new DynamicProgramming().lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}, 1);
    }
}
