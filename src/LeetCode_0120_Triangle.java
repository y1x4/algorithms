import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode_0120_Triangle {

    /**
     * 固定最长的一条边，双指针进行扫描
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[]{triangle.get(0).get(0)};
        int res = dp[0];
        for (int i = 1; i < n; i++) {
            int[] dp2 = new int[i + 1];
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    dp2[j] = dp[j] + triangle.get(i).get(j);
                    res = dp2[0];
                } else if (j == i) {
                    dp2[j] = dp[j - 1] + triangle.get(i).get(j);
                } else {
                    dp2[j] = Math.min(dp[j - 1], dp[j]) + triangle.get(i).get(j);
                }
                res = Math.min(res, dp2[j]);
            }
            dp = dp2;
        }
        return res;
    }

    public static void main(String[] args) {
        LeetCode_0120_Triangle obj = new LeetCode_0120_Triangle();
        assert obj.minimumTotal(List.of(List.of(2), List.of(3, 4), List.of(6, 5, 7), List.of(4, 1, 8, 3))) == 11;
    }
}
