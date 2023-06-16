import java.util.ArrayList;
import java.util.List;

public class LeetCode_0078_Subsets {

    /**
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * All the integers of nums are unique.
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        subsets(nums, new ArrayList<>(), res, nums.length, 0);
        return res;
    }

    private void subsets(int[] nums, List<Integer> list, List<List<Integer>> res, int n, int i) {
        if (i == n) {
            res.add(new ArrayList<>(list));
        } else {
            // add current num to list
            list.add(nums[i]);
            subsets(nums, list, res, n, i + 1);
            // not add current num to list
            list.remove(list.size() - 1);
            subsets(nums, list, res, n, i + 1);
        }
    }

    // nums=[1, 2, 3], i=0...7
    public List<List<Integer>> subsets2(int[] nums) {
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            list.clear();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << n)) != 0) {
                    list.add(nums[i]);
                }
            }
            res.add(new ArrayList<>(list));
        }
        return res;
    }

    // nums=[1, 2, 3], num of lists add to res is 1, 2, 4
    public List<List<Integer>> subsets3(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // add empty list first
        res.add(new ArrayList<>());
        for (int num : nums) {
            int size = res.size();
            for (int j = 0; j < size; j++) {
                List<Integer> list = new ArrayList<>(res.get(j));
                list.add(num);
                res.add(list);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LeetCode_0078_Subsets obj = new LeetCode_0078_Subsets();
    }
}
