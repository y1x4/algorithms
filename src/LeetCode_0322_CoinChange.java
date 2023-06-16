import java.util.Arrays;

/**
 * @author yixu
 * @date 2022-09-24 17:08:31
 */
public class LeetCode_0322_CoinChange {

    /**
     * 1. Listen: an integer array coins and an integer amount, each kind of coin is infinite,
     *  Return the fewest number of coins that you need to make up that amount, or -1.
     * 2. Constraints:
     *  1 <= coins.length <= 12
     *  1 <= coins[i] <= 231 - 1
     *  0 <= amount <= 104
     *
     *  dp[i][j]: answer of i kinds of coin to make up j.
     *  dp[i][0]=0.
     *  dp[i][j]=min(dp[i][j-coins[i]]+1,dp[i-1][j])
     *
     * time: O(N*amount)
     * space: O(N*amount)
     */
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int n = coins.length;
        int[][] dp = new int[n][amount + 1];
        // initialize
        int defaultVal = Integer.MAX_VALUE - 1;
        for (int[] arr : dp) {
            for (int j = 1; j <= amount; j++) {
                arr[j] = defaultVal;
            }
        }
        for (int j = 1; j <= amount; j++) {
            if (j % coins[0] == 0) {
                dp[0][j] = j / coins[0];
            }
        }

        // dp calculate
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - coins[i] >= 0 && dp[i][j - coins[i]] + 1 < dp[i - 1][j]) {
                    dp[i][j] = dp[i][j - coins[i]] + 1;
                }
            }
        }
        for (int[] arr : dp) {
            System.out.println(Arrays.toString(arr));
        }
        return dp[n - 1][amount] == defaultVal ? -1 : dp[n - 1][amount];
    }


    public static void main(String[] args) {
        LeetCode_0322_CoinChange test = new LeetCode_0322_CoinChange();
        test.coinChange(new int[]{2, 5, 10, 1}, 27);
//        assert test.coinChange(new int[]{1, 2, 5}, 11) == 3;
//        assert test.coinChange(new int[]{2}, 3) == -1;
//        assert test.coinChange(new int[]{1}, 0) == 0;
//        assert test.coinChange(new int[]{2, 5, 10, 1}, 27) == 4;
    }
}
