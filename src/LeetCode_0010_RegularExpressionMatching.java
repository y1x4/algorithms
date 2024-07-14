import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        // set1存储数组1里的数
        HashSet<Integer> set1 = new HashSet<>();
        for(int num : nums1) {
            set1.add(num);
        }

        // set2存储数组2里的数
        HashSet<Integer> set2 = new HashSet<>();
        for(int num : nums2) {
            set2.add(num);
        }

        // 遍历数组2里的数，从set1移除存在于数组2里的数
        for(int num : nums2) {
            if (set1.contains(num)) {
                set1.remove(num);
            }
        }

        // 遍历数组1里的数，从set2移除存在于数组1里的数
        for(int num : nums1) {
            if(set2.contains(num)) {
                set2.remove(num);
            }
        }

        // 组装结果到列表中，返回
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>(set1));
        res.add(new ArrayList<>(set2));
        return res;
    }


    public static void main(String[] args) {
        LeetCode_0010_RegularExpressionMatching obj = new LeetCode_0010_RegularExpressionMatching();

        StringBuilder sb = new StringBuilder("insert into user_1 values ");
        String format = "(%d,%d,%d,'%d@qq.com'), ";
        for (int i = 5; i <= 500; i+=5) {
            sb.append(String.format(format, i, i, i, i));
        }
        sb.setCharAt(sb.length() - 2, ';');
        System.out.println(sb);
    }
}
