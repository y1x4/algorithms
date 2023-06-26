import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode_0611_ValidTriangleNumber {

    /**
     * 固定最长的一条边，双指针进行扫描
     */
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int n = nums.length;
        int res = 0;
        for (int i = n - 1; i >= 2; --i) {
            int l = 0, r = i - 1;
            while (l < r) {
                if (nums[l] + nums[r] > nums[i]) {
                    res += r - l;
                    --r;
                } else {
                    ++l;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LeetCode_0611_ValidTriangleNumber obj = new LeetCode_0611_ValidTriangleNumber();
        assert obj.triangleNumber(new int[]{2, 2, 3, 4}) == 3;
        assert obj.triangleNumber(new int[]{4, 2, 3, 4}) == 4;
    }
}
