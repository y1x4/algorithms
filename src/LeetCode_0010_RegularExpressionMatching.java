import java.util.List;

public class LeetCode_0010_RegularExpressionMatching {

    /**
     * dp[i][j]
     */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();

        // dp[i][j] represents if s[1:i] match p[1:j], initialize dp status
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        // 匹配0次或1次（重复则变成匹配多次）
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                    } else {
                        // 不同则只能匹配0次
                        dp[i][j] = dp[i][j - 2];
                    }
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        LeetCode_0010_RegularExpressionMatching obj = new LeetCode_0010_RegularExpressionMatching();
    }
}
