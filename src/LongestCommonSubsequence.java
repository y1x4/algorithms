/**
 *
 * Created by yixu on 2019/11/10.
 */
public class LongestCommonSubsequence {


    public int longestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0)
            return 0;

        int m = s1.length(), n = s2.length();

        // dp[i][j] is lcs(s1[:i], s2[:j])
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }

        return dp[m][n];
    }


    public static void main(String[] args) {
        new LongestCommonSubsequence().longestCommonSubsequence("GXTXAYB", "AGGTAB");
    }
}
