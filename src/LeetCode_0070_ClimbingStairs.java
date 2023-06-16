/**
 * @author yixu
 * @date 2022-09-24 16:41:24
 */
public class LeetCode_0070_ClimbingStairs {

    /**
     * 1. Listen: n step, each time 1 or 2 steps, how many distinct ways to the top? Constraints: 1 <= n <= 45.
     * 2. Examples: make some small or special examples, debug them.
     * 3. Brute Force: get and state a brute force solution and its runtime, then optimize.
     * 4. Optimization: BUD Optimization - Bottlenecks, Unnecessary Work, Duplicated Work.
     * 5. Walk Through: walk through the optimized solution in detail before coding.
     * 6. Implement: write beautiful code.
     * 7. Test.
     *
     * time: O(N)
     * space: O(1)
     */
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int fn_2 = 1, fn_1 = 2, fn = 3;
        for (int i = 3; i <= n; i++) {
            // calculate current value
            fn = fn_1 + fn_2;
            // prepare for next loop
            fn_2 = fn_1;
            fn_1 = fn;
        }
        return fn;
    }

    public static void main(String[] args) {
        LeetCode_0070_ClimbingStairs test = new LeetCode_0070_ClimbingStairs();
        assert test.climbStairs(1) == 1;
        assert test.climbStairs(2) == 2;
        assert test.climbStairs(3) == 3;
        System.out.println(test.climbStairs(45));
    }
}
