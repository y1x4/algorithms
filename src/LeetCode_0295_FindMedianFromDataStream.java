import java.util.Comparator;
import java.util.PriorityQueue;

public class LeetCode_0295_FindMedianFromDataStream {

    /**
     * 1 <= task.length <= 10^4
     * tasks[i] is upper-case English letter.
     * The integer n is in the range [0, 100].
     *
     * Time:  addNum O(logN), findMedian O(1)
     * Space: O(N)
     */
    class MedianFinder {

        private PriorityQueue<Integer> leftHeap;
        private PriorityQueue<Integer> rightHeap;

        public MedianFinder() {
            leftHeap = new PriorityQueue<>(Comparator.reverseOrder());  // max heap
            rightHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (leftHeap.isEmpty() || num <= leftHeap.peek()) {
                leftHeap.add(num);
                if (leftHeap.size() > rightHeap.size() + 1) {
                    rightHeap.add(leftHeap.remove());
                }
            } else {
                rightHeap.add(num);
                if (rightHeap.size() > leftHeap.size()) {
                    leftHeap.add(rightHeap.remove());
                }
            }
        }

        public double findMedian() {
            if (leftHeap.isEmpty())
                return 0;
            return leftHeap.size() == rightHeap.size() ? (leftHeap.peek() + rightHeap.peek()) / 2.0 : leftHeap.peek();
        }
    }


    public static void main(String[] args) {
        LeetCode_0295_FindMedianFromDataStream obj = new LeetCode_0295_FindMedianFromDataStream();
        PriorityQueue<Integer> rightHeap = new PriorityQueue<>(Comparator.reverseOrder());
        rightHeap.add(2);
        rightHeap.add(3);
        System.out.println(rightHeap.peek());
    }
}
