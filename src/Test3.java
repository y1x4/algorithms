public class Test3 {

    // 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
    public static int kth(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length == 0 || k > nums.length) {
            throw new IllegalArgumentException();
        }

        int n = nums.length, low = 0, high = n - 1, index;
        while (true) {
            index = partition(nums, low, high);
            int cmp = index - k + 1;
            if (cmp == 0) {
                return nums[index];
            } else if (cmp < 0) {
                low = index + 1;
            } else {
                high = index - 1;
            }
        }
    }

    public static int partition(int[] nums, int i, int j) {
        int value = nums[i];
        while (i < j) {
            while (i < j && nums[j] < value) {
                j--;
            }
            nums[i] = nums[j];
            while (i < j && nums[i] >= value) {
                i++;
            }
            nums[j] = nums[i];
        }
        nums[i] = value;
        return i;
    }

    public static void main(String[] args) {
        System.out.println(kth(new int[]{6, 2, 5, 3, 1, 4}, 1));
        System.out.println(kth(new int[]{6, 2, 5, 3, 1, 4}, 2));
        System.out.println(kth(new int[]{6, 2, 5, 3, 1, 4}, 3));
        System.out.println(kth(new int[]{6, 2, 5, 3, 1, 4}, 4));
        System.out.println(kth(new int[]{6, 2, 5, 3, 1, 4}, 5));
        System.out.println(kth(new int[]{6, 2, 5, 3, 1, 4}, 6));
    }
}

