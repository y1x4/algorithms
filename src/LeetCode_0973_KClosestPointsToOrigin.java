import java.util.Arrays;

public class LeetCode_0973_KClosestPointsToOrigin {

    /**
     * 1 <= k <= points.length <= 104
     * -104 < xi, yi < 104
     */
    public int[][] kClosest(int[][] points, int k) {
        int n = points.length, low = 0, high = n - 1, mid;
        while (low < high) {
            mid = partition(points, low, high);
            if (mid > k) {
                high = mid - 1;
            } else if (mid < k) {
                low = mid + 1;
            } else {
                break;
            }
        }
        return Arrays.copyOfRange(points, 0, k);
    }

    private int partition(int[][] points, int low, int high) {
        int[] pivot = points[low];
        double pivotVal = distance(pivot);
        while (low < high) {
            while (low < high && Double.compare(distance(points[high]), pivotVal) > 0) {
                high--;
            }
            points[low] = points[high];
            while (low < high && Double.compare(distance(points[low]), pivotVal) <= 0) {
                low++;
            }
            points[high] = points[low];
        }
        points[low] = pivot;
        return low;
    }

    private double distance(int[] p) {
        return Math.pow(p[0], 2) + Math.pow(p[1], 2);
    }

    public static void main(String[] args) {
        LeetCode_0973_KClosestPointsToOrigin obj = new LeetCode_0973_KClosestPointsToOrigin();
    }
}
