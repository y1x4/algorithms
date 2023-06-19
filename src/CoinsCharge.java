public class CoinsCharge {


    /** 所需最少硬币数，若不能凑成返回-1 */
    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        int max = amount + 1;   // can't combine
        for (int i = 1; i < dp.length; i++) {
            dp[i] = max;
        }

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin > i) {
                    continue;
                }
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }


    /** 能凑成的组合数 */
    public static int change(int amount, int[] coins) {
        if (coins.length * amount == 0) {
            return amount == 0 ? 1 : 0;
        }

        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
//            for (int j = 1; j <= amount; j++) {
//                if (coin > j) {
//                    continue;
//                }
//                dp[j] += dp[j - coin];
//            }
            for (int j = coin; j <= amount; j++) {
                dp[j] += dp[j - coin];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1, 2, 5}, 11)); // 3
        System.out.println(coinChange(new int[]{2}, 3));    // -1
        System.out.println(change(5, new int[]{1, 2, 5})); // 4
        System.out.println(change(3, new int[]{2}));    // 0


    }
}
