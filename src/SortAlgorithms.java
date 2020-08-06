import java.util.ArrayList;
import java.util.Arrays;

public class SortAlgorithms {

    public static void shellSort(int[] arr) {
        if (arr == null || arr.length <= 1)
            return;

        int n = arr.length;
        int gap = 1;
        while (gap < n / 3) {
            gap = gap * 3 + 1;
        }

        int temp;
        for ( ; gap > 0; gap /= 3) {
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




    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }



    public int[] sort(int[] sourceArray) {
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
        for (int i = (len >> 1)-1; i >= 0; i--) {
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


    // 基数排序
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



    public static void main(String[] args) {
        int[] arr = new int[]{6, 2, 22, 45, 1, 6, 8, 200, 56, 111};
//        shellSort(arr);
//        heapSort(arr);
        System.out.println(Arrays.toString(radixSort(arr, 10, 3)));
    }
}
