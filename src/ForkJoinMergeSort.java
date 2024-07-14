import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.IntStream;

public class ForkJoinMergeSort extends RecursiveAction {

    int[] nums, temp;
    int lo, hi;

    public ForkJoinMergeSort(int[] n, int[] t, int l, int h) {
        nums = n;
        temp = t;
        lo = l;
        hi = h;
    }

    @Override
    public void compute() {
//        System.out.println(String.format("%d-%d, %s", lo, hi, Thread.currentThread().getName()));
        if (lo < hi) {
            // 划分子任务并提交
            int mid = lo + ((hi - lo) >> 1);
            ForkJoinMergeSort leftTask = new ForkJoinMergeSort(nums, temp, lo, mid);
            ForkJoinMergeSort rightTask = new ForkJoinMergeSort(nums, temp, mid + 1, hi);
//            leftTask.fork();
//            rightTask.fork();
            invokeAll(leftTask, rightTask);
            // 等待任务执行完毕
            leftTask.join();
            rightTask.join();
            // 合并子任务结果
            merge(nums, temp, lo, mid, hi);
        }
    }

    public static void merge(int[] nums, int[] temp, int lo, int mid, int hi) {
        int left = lo, right = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (left > mid) {
                temp[i] = nums[right++];
            } else if (right > hi) {
                temp[i] = nums[left++];
            } else if (nums[left] <= nums[right]) {
                temp[i] = nums[left++];
            } else {
                temp[i] = nums[right++];
            }
        }
        System.arraycopy(temp, lo, nums, lo, hi - lo + 1);
    }


    public static void main(String[] args) {
        // 3351ms vs 19834ms
        int[] nums = generateRandomArray(1, 100000000, 100000000);
        int[] temp = new int[nums.length];
        int[] nums2 = Arrays.copyOf(nums, nums.length);

        ForkJoinPool forkjoinPool = new ForkJoinPool();
        long start = System.currentTimeMillis();
        ForkJoinMergeSort mergeSortTask = new ForkJoinMergeSort(nums, temp, 0, nums.length - 1);
        forkjoinPool.invoke(mergeSortTask);
        System.out.println("time cost: " + (System.currentTimeMillis() - start));

        for (int i = 0; i < 1000; i++) {
            System.out.print(nums[i]);
            System.out.print(',');
        }
//        System.out.println(Arrays.toString(nums));

//        long start2 = System.currentTimeMillis();
//        mergeSort(nums2, temp, 0, nums2.length - 1);
//        System.out.println("time cost: " + (System.currentTimeMillis() - start2));

//        System.out.println(Arrays.toString(nums2));
    }

    public static int[] generateRandomArray(int min, int max, int size) {
        return IntStream
                .generate(() -> min + new Random().nextInt(max - min + 1))
                .limit(size)
                .toArray();
    }

    public static void mergeSort(int[] nums, int[] temp, int l, int r) {
        if (l < r) {
            int mid = l + ((r - l) >> 1);
            mergeSort(nums, temp, l, mid);
            mergeSort(nums, temp, mid + 1, r);
            merge(nums, temp, l, mid, r);
        }
    }
}
