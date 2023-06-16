public class LeetCode_0033_SearchInRotatedSortedArray {

    /**
     * 1 <= nums.length <= 5000
     * -104 <= nums[i] <= 104
     * All values of nums are unique.
     * nums is an ascending array that is possibly rotated.
     * -104 <= target <= 104
     *
     * compare with nums[0] and nums[n-1], find the sorted part and compare
     *
     * Time:  O(logN)
     * Space: O(1)
     */
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 1)
            return nums[0] == target ? 0 : -1;

        int lo = 0, hi = n - 1, mid;
        while (lo < hi) {
            mid = lo + ((hi - lo) >> 1);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return nums[lo] == target ? lo : -1;
    }

    public static void main(String[] args) {
        LeetCode_0033_SearchInRotatedSortedArray obj = new LeetCode_0033_SearchInRotatedSortedArray();
        System.out.println(obj.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0)); // 4
    }
}
