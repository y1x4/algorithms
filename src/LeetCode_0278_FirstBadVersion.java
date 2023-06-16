public class LeetCode_0278_FirstBadVersion {

    /**
     * 1 <= bad <= n <= 231 - 1
     *
     * Time:  O(logN)
     * Space: O(1)
     */
    public int firstBadVersion(int n) {
        int lo = 1, hi = n, mid;

        if (isBadVersion(lo))
            return lo;

        while (lo < hi) {
            mid = lo + ((hi - lo) >>> 1);
            if (isBadVersion(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    boolean isBadVersion(int version) {
        if (version >= 4)
            return true;
        return false;
    }

    public static void main(String[] args) {
        LeetCode_0278_FirstBadVersion obj = new LeetCode_0278_FirstBadVersion();
        System.out.println(obj.firstBadVersion(5)); // 4
    }
}
