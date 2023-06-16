import java.util.Arrays;
import java.util.Comparator;

public class LeetCode_1235_MaximumProfitInJobScheduling {

    /**
     * 1 <= startTime.length == endTime.length == profit.length <= 5 * 104
     * 1 <= startTime[i] < endTime[i] <= 109
     * 1 <= profit[i] <= 104
     *
     * 1. sort jobs by endTime;
     * 2. dp[i]: max profit in previous i jobs
     *      dp[i] = max(dp[i-1], dp[k]+profit[i]), k = jobs endTime <= job i's startTime, searched by binary search
     *
     * Time:  O(N*logN)
     * Space: O(N)
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;

        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, Comparator.comparingInt(job -> job[1]));

        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int index = firstIndexGT(jobs, i - 1, jobs[i - 1][0]);
            dp[i] = Math.max(dp[i - 1], dp[index] + jobs[i - 1][2]);
        }
        return dp[n];
    }

    private int firstIndexGT(int[][] jobs, int hi, int target) {
        int lo = 0, mid;

        while (lo < hi) {
            mid = (lo + hi) >>> 1;
            if (jobs[mid][1] <= target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    private int lastIndexLE(int[][] jobs, int hi, int target) {
        int lo = 0, mid;

        while (lo < hi) {
            mid = (lo + hi) >>> 1;
            if (jobs[mid][1] < target + 1) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo - 1;
    }


    public static void main(String[] args) {
        LeetCode_1235_MaximumProfitInJobScheduling obj = new LeetCode_1235_MaximumProfitInJobScheduling();
    }
}
