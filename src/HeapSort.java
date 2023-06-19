import java.util.Arrays;
import java.util.Objects;

public class HeapSort {

    public static void sort(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return;
        }

        // 1.将数组堆化
        int n = arr.length;
        int beginIndex = (n >> 1) - 1;
        for (int i = beginIndex; i >= 0; i--) {
            maxHeapifyLoop(arr, n, i);
        }

        // 2.排序
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, 0);
            maxHeapifyLoop(arr, i, 0);
        }
    }

    private static void maxHeapify(int[] arr, int len, int i) {
        int leftIdx = (i << 1) + 1;
        if (leftIdx >= len) {
            return;
        }
        int rightIdx = leftIdx + 1;
        int cMax = leftIdx;
        if (rightIdx < len && arr[rightIdx] > arr[leftIdx]) {
            cMax = rightIdx;
        }
        if (arr[cMax] > arr[i]) {
            swap(arr, cMax, i);
            maxHeapify(arr, len, cMax);
        }
    }

    private static void maxHeapifyLoop(int[] arr, int len, int i) {
        int leftIdx, rightIdx, cMax;
        while ((leftIdx = (i << 1) + 1) < len) {
            rightIdx = leftIdx + 1;
            cMax = leftIdx;
            if (rightIdx < len && arr[rightIdx] > arr[leftIdx]) {
                cMax = rightIdx;
            }

            if (arr[cMax] > arr[i]) {
                swap(arr, cMax, i);
                i = cMax;
            } else {
                break;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 3, 0, 8, 6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8, 9, 7, 3, 1, 2, 5, 9, 7, 4, 0, 2, 6};
        HeapSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
