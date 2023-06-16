import java.util.Arrays;
import java.util.Comparator;

public class LeetCode_0435_NonOverlappingIntervals {

    public int eraseOverlapIntervals(int[][] intervals) {

        // sort by interval right
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[1]));

        int right = intervals[0][1];
        int numOfSelected = 1;
        int n = intervals.length;
        for (int i = 1; i < n; i++) {
            // if interval i 's left >=  current right, add interval i
            if (intervals[i][0] >= right) {
                right = intervals[i][1];
                numOfSelected++;
            }
        }
        // return min num of intervals could be removed
        return n - numOfSelected;
    }

}
