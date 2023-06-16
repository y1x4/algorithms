import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayAlgorithms {

    /**
     * 3种解法：
     * 1.暴力解法，Arrays.sort()，JDK中使用快速排序，时间复杂度O(NlogN)，空间复杂度O(logN)；
     * 2.不断切分区间、排序，使用主定理进行分析，时间复杂度O(N)，空间复杂度O(1)；
     * 3.大小为K的最小堆，时间复杂度O(NlogK)，空间复杂度O(K)。
     */
    public static int kthLargestNumber(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || nums.length < k) {
            System.out.println("throw new IllegalArgumentException(\"入参不符合要求\")");
            return -1;
        }

        int lo = 0, hi = nums.length - 1, pivotIdx;
        while (lo < hi) {
            pivotIdx = partition(nums, lo, hi);
            if (pivotIdx == k - 1)
                return nums[pivotIdx];
            else if (pivotIdx < k - 1)
                lo = pivotIdx + 1;
            else
                hi = pivotIdx - 1;
        }
        return nums[lo];
    }

    private static int partition(int[] nums, int i, int j) {
        int pivot = nums[i];
        while (i < j) {
            while (i < j && nums[j] >= pivot)
                j--;
            nums[i] = nums[j];
            while (i < j && nums[i] < pivot)
                i++;
            nums[j] = nums[i];
        }
        nums[i] = pivot;
        return i;
    }

    /**
     * 48.顺时针旋转图像
     * 1.坐标替换，划分为4半，每个点的4个位置轮换
     * 2.水平+对角线翻转
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int temp;
        for (int row = 0; row < (n >> 1); row++) {
            for (int col = 0; col < ((n + 1) >> 1); col++) {
                temp = matrix[row][col];
                matrix[row][col] = matrix[n - 1 - col][row];
                matrix[n - 1 - col][row] = matrix[n - 1 - row][n - 1 - col];
                matrix[n - 1 - row][n - 1 - col] = matrix[col][n - 1 - row];
                matrix[col][n - 1 - row] = temp;
            }
        }
    }

    /**
     * 51.N皇后
     */
    public List<List<String>> solveNQueens(int n) {
        int[] record = new int[n];
        Arrays.fill(record, -1);
        List<List<String>> res = new ArrayList<>();
        solveNQueens(record, res, n, 0);
        return res;
    }

    private void solveNQueens(int[] record, List<List<String>> res, int n, int i) {
        if (i == n) {
            List<String> solution = new ArrayList<>();
            for (int r = 0; r < n; r++) {
                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < n; c++) {
                    sb.append((record[c] == r) ? 'Q' : '.');
                }
                solution.add(new String(sb));
            }
            res.add(solution);
        } else {
            for (int j = 0; j < n; j++) {
                if (isQueenValid(record, i, j)) {
                    record[j] = i;
                    solveNQueens(record, res, n, i + 1);
                    record[j] = -1;
                }
            }
        }
    }

    private boolean isQueenValid(int[] record, int i, int j) {
        // 行没有，因为每行只有一个
        // 列，record里非-1值
        if (record[j] != -1) {
            return false;
        }
        // 斜
        int n = record.length;
        for (int r = i - 1, c = j - 1; r >= 0 && c >= 0; r--, c--) {
            if (record[c] == r) {
                return false;
            }
        }
        for (int r = i - 1, c = j + 1; r >= 0 && c < n; r--, c++) {
            if (record[c] == r) {
                return false;
            }
        }
        for (int r = i + 1, c = j + 1; r < n && c < n; r++, c++) {
            if (record[c] == r) {
                return false;
            }
        }
        for (int r = i + 1, c = j - 1; r < n && c >= 0; r++, c--) {
            if (record[c] == r) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        new ArrayAlgorithms().solveNQueens(4);


    }
}

class DListNode {
    int key, val;
    DListNode prev, next;

    DListNode(int k, int v) {
        key = k;
        val = v;
    }
}

class LRUCache {

    Map<Integer, DListNode> map;
    int size;
    int capacity;
    DListNode head, tail;   // 前后都有哨兵

    public LRUCache(int cap) {
        map = new HashMap<>(cap);
        size = 0;
        capacity = cap;
        head = new DListNode(-1, -1);
        tail = new DListNode(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DListNode node = map.get(key);
        if (node == null)
            return -1;

        remove(node);
        addToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        DListNode node = map.get(key);
        if (node != null) {
            node.val = value;
            remove(node);
            addToHead(node);
        } else {
            if (size == capacity) {
                DListNode removeNode = tail.prev;
                map.remove(removeNode.key);
                remove(removeNode);
                size--;
            }
            node = new DListNode(key, value);
            addToHead(node);
            map.put(key, node);
            size++;
        }
    }

    private void addToHead(DListNode node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    private void remove(DListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

// 23/26，超时了
class LFUCache {
    int capacity;
    int size;
    Map<Integer, Integer> freqMap;
    Map<Integer, DListNode> nodeMap;
    DListNode head, tail;   // 前后都有哨兵

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.freqMap = new HashMap<>();
        this.nodeMap = new HashMap<>(capacity);
        head = new DListNode(-1, -1);
        tail = new DListNode(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DListNode node = nodeMap.get(key);
        if (null == node) {
            return -1;
        }
        Integer freq = freqMap.get(key);
        freqMap.put(key, ++freq);
        DListNode prev = node.prev;
        while (prev != head && freqMap.get(prev.key) <= freq) {
            prev = prev.prev;
        }
        // 解绑node，移到prev之后
        if (prev != node.prev) {
            removeNode(node);
            addNodeAfter(prev, node);

        }
        return node.val;
    }

    public void put(int key, int value) {
        if (this.capacity <= 0) {
            return;
        }
        DListNode node, prev;
        Integer freq = freqMap.get(key);
        if (null == freq) {
            freq = 1;
            freqMap.put(key, freq);
            node = new DListNode(key, value);
            nodeMap.put(key, node);
            if (++size > capacity) {
                nodeMap.remove(tail.prev.key);
                freqMap.remove(tail.prev.key);
                removeNode(tail.prev);
            }
            prev = tail.prev;
        } else {
            freqMap.put(key, ++freq);
            node = nodeMap.get(key);
            node.val = value;
            prev = node.prev;
            removeNode(node);
        }
        // 更新node
        while (prev != head && freqMap.get(prev.key) <= freq) {
            prev = prev.prev;
        }
        addNodeAfter(prev, node);
    }

    private void removeNode(DListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNodeAfter(DListNode prev, DListNode node) {
        node.next = prev.next;
        node.prev = prev;
        prev.next.prev = node;
        prev.next = node;
    }

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        int temp = 1;
        res[0] = temp;
        for (int i = 1; i < n; i++) {
            temp *= nums[i - 1];
            res[i] = temp;
        }

        temp = 1;
        for (int i = n - 2; i >= 0; i--) {
            temp *= nums[i + 1];
            res[i] *= temp;
        }

        return res;
    }
}