import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LeetCode_0046_Permutations {

    /**
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * All the integers of nums are unique.
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        permute(Arrays.stream(nums).boxed().collect(Collectors.toList()), res, nums.length, 0);
        return res;
    }

    private void permute(List<Integer> list, List<List<Integer>> res, int n, int i) {
        if (i == n) {
            res.add(new ArrayList<>(list));
        } else {
            for (int j = i; j < n; j++) {
                Collections.swap(list, i, j);
                permute(list, res, n, i + 1);
                Collections.swap(list, i, j);
            }
        }
    }

    public static void main(String[] args) {
        LeetCode_0046_Permutations obj = new LeetCode_0046_Permutations();
    }
}
