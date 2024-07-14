import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test2 {

    // 有序数组，向左移动了n次，求n
    // 1234567 移动后 4567123
    // 1,2,3,4,5,6

    public static int move(int[] nums) {
        int n = nums.length;
        int low = 0, high = n - 1, mid;
        while (low < high) {
            if (nums[low] < nums[high]) {
                break;
            }
            mid = low + ((high - low) >> 1);
            if (nums[mid] < nums[high]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low == 0 ? 0 : n - low;
    }

    public static void main(String[] args) {
        System.out.println(move(new int[]{4, 5, 6, 7, 1, 2, 3}));
        System.out.println(move(new int[]{1, 2, 3, 4, 5, 6, 7}));
    }
}


// 业务
