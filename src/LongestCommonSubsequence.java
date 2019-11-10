import java.util.Arrays;

/**
 *
 * Created by yixu on 2019/11/10.
 */
public class LongestCommonSubsequence {


    /**
     * 求两个字符串的最长公共子串的长度。
     * dp[i][j] = LCS(s1[:i], s2[:j])
     *      = 0 if i == 0 or j == 0
     *      = dp[i-1][j-1] + 1 if i,j > 0 and s1[i-1] != s2[j-1]
     *      = max(dp[i][j-1], dp[i-1][j]) if i,j > 0 and s1[i-1] == s2[j-1]
     * 该算法的空间、时间复杂度均为 O(n^2)，经过优化后，空间复杂度可为 O(n)。
     * @return length
     * */
    public int longestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0)
            return 0;

        int m = s1.length(), n = s2.length();
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


    /**
     * 空间优化，每次迭代只需要两个数组
     * */
    public int longestCommonSubsequenceOPT(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0)
            return 0;

        int m = s1.length(), n = s2.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    curr[j + 1] = prev[j] + 1;
                } else {
                    curr[j + 1] = Math.max(prev[j + 1], curr[j]);
                }
            }

            prev = curr;
        }

        return curr[n];
    }


    /**
     * 记录，输出公共子序列
     * */
    public void longestCommonSubsequencePrint(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0)
            return;

        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        char[][] notes = new char[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            notes[i + 1][0] = s1.charAt(i);
            for (int j = 0; j < n; j++) {
                notes[0][j + 1] = s2.charAt(j);
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                    notes[i + 1][j + 1] = 'x';
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                    if (dp[i + 1][j] > dp[i][j + 1])
                        notes[i + 1][j + 1] = '<';
                    else
                        notes[i + 1][j + 1] = '^';
                }
            }
        }

        char[] res = new char[dp[m][n]];
        int i = m, j = n, idx = dp[m][n] - 1;
        while (i > 0 && j > 0) {
            if (notes[i][j] == 'x') {
                res[idx--] = s1.charAt(i - 1);
                i--;
                j--;
            } else if (notes[i][j] == '<')
                j--;
            else
                i--;
        }


        for (int[] arr : dp)
            System.out.println(Arrays.toString(arr));

        for (char[] chars : notes)
            System.out.println(Arrays.toString(chars));
        System.out.println(Arrays.toString(res));
    }


    public static void main(String[] args) {
        new LongestCommonSubsequence().longestCommonSubsequence("GXTXAYB", "AGGTAB");   // 4
        new LongestCommonSubsequence().longestCommonSubsequenceOPT("GXTXAYB", "AGGTAB");
        new LongestCommonSubsequence().longestCommonSubsequencePrint("GXTXAYB", "AGGTAB");
    }
}
