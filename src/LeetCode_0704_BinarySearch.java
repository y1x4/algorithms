public class LeetCode_0704_BinarySearch {

    /**
     * 1 <= nums.length <= 104
     * -104 < nums[i], target < 104
     * All the integers in nums are unique.
     * nums is sorted in ascending order.
     * <p>
     * Time:  O(logN)
     * Space: O(1)
     */
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid;
        if (nums[lo] > target || nums[hi] < target)
            return -1;

        while (lo < hi) {
            mid = (lo + hi) >>> 1;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return nums[lo] == target ? lo : -1;
    }


    public static void main(String[] args) {
        LeetCode_0704_BinarySearch obj = new LeetCode_0704_BinarySearch();
    }
}
