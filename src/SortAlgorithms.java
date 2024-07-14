import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SortAlgorithms {

    /**
     * 4.希尔排序
     */
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        int gap = 1;
        while (gap < n / 3) {
            gap = gap * 3 + 1;
        }

        int temp;
        for (; gap > 0; gap /= 3) {
            for (int i = gap; i < n; i++) {
                temp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = temp;
            }
        }
    }


    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    /**
     * 7.堆排序（最大堆）
     */
    public int[] heapSort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int n = arr.length;
        buildMaxHeap(arr, n);

        // 对堆化数据排序
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            maxHeapify(arr, 0, i);
        }
        return arr;
    }

    private void buildMaxHeap(int[] arr, int len) {
        // 从第一个非叶子节点开始
        for (int i = (len >> 1) - 1; i >= 0; i--) {
            maxHeapify(arr, i, len);
        }
    }

    // 不断向下调整，使其符合堆的特性
    private void maxHeapify(int[] arr, int i, int len) {
        int left = (i << 1) + 1;
        if (left >= len)
            return;
        int right = left + 1;

        int largest = left;
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if (arr[largest] > arr[i]) {
            swap(arr, i, largest);
            maxHeapify(arr, largest, len);
        }
    }


    /**
     * 8.计数排序
     */
    public static int[] countingSort(int[] sourceArr, int maxValue) {
        int[] buckets = new int[maxValue + 1];
        for (int value : sourceArr) {
            buckets[value]++;
        }

        int[] arr = new int[sourceArr.length];
        int idx = 0;
        for (int val = 0; val <= maxValue; val++) {
            for (int i = 0; i < buckets[val]; i++) {
                arr[idx++] = val;
            }
        }
        return arr;
    }


    /**
     * 10.基数排序
     */
    public static int[] radixSort(int[] sourceArray, int radix, int maxDigit) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(radix);
        for (int i = 0; i < radix; i++) {
            buckets.add(new ArrayList<>());
        }

        int mod = radix, dev = 1;
        for (int i = 0; i < maxDigit; i++, mod *= radix, dev *= radix) {
            for (int num : arr) {
                buckets.get((num % mod) / dev).add(num);
            }

            int idx = 0;
            for (ArrayList<Integer> bucket : buckets) {
                for (int num : bucket) {
                    arr[idx++] = num;
                }
                bucket.clear();
            }
        }
        return arr;
    }


    public static int[] quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    private static void quickSort(int[] nums, int lo, int hi) {
        if (hi > lo) {
            int pivotIndex = partition(nums, lo, hi);
            quickSort(nums, lo, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, hi);
        }
    }

    private static int partition(int[] nums, int lo, int hi) {
        int randomIndex = lo + new Random().nextInt(hi - lo + 1);
        swap(nums, lo, randomIndex);

        int pivot = nums[lo];
        while (lo < hi) {
            while (lo < hi && nums[hi] >= pivot) {
                hi--;
            }
            nums[lo] = nums[hi];
            while (lo < hi && nums[lo] < pivot) {
                lo++;
            }
            nums[hi] = nums[lo];
        }
        nums[lo] = pivot;
        return lo;
    }

    public static void mergeSort(int[] nums) {
        int[] temp = new int[nums.length];
        mergeSort(nums, temp, 0, nums.length - 1);
    }

    public static void mergeSort(int[] nums, int[] temp, int l, int r) {
        if (l < r) {
            int mid = l + ((r - l) >> 1);
            mergeSort(nums, temp, l, mid);
            mergeSort(nums, temp, mid + 1, r);
            merge(nums, temp, l, mid, r);
        }
    }

    private static void merge(int[] nums, int[] temp, int l, int mid, int r) {
        int left = l, right = mid + 1;
        for (int i = l; i <= r; i++) {
            if (left > mid) {
                temp[i] = nums[right++];
            } else if (right > r) {
                temp[i] = nums[left++];
            } else if (nums[left] <= nums[right]) {
                temp[i] = nums[left++];
            } else {
                temp[i] = nums[right++];
            }
        }
        System.arraycopy(temp, l, nums, l, r - l + 1);
    }


    public static void main(String[] args) {
        int[] arr = new int[]{6, 2, 22, 45, 1, 6, 8, 200, 56, 111};
//        shellSort(arr);
//        heapSort(arr);
//        System.out.println(Arrays.toString(radixSort(arr, 10, 3)));
//        System.out.println(Arrays.toString(quickSort(arr)));
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
