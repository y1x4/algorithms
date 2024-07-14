import java.util.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class LeetCode_0021_MergeTwoSortedLists {

    /**
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both list1 and list2 are sorted in non-decreasing order.
     *
     * @see LeetCode_0023_MergeKSortedLists
     * <p>
     * Time:  O(min(m, n))
     * Space: O(1)
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode dummy = new ListNode(-1), tail = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }

            tail = tail.next;
        }

        tail.next = l1 != null ? l1 : l2;
        return dummy.next;
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
            head = head.next;
        }

        ListNode newHead = null, next;
        for (int i = left; i <= right; i++) {
            next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }

        prev.next.next = head;
        prev.next = newHead;
        return dummy.next;
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy, curr = head;
        while (curr != null) {
            if (curr.next != null && curr.val == curr.next.val) {
                while (curr.next != null && curr.val == curr.next.val) {
                    curr = curr.next;
                }
                curr = curr.next;
            } else {
                prev.next = curr;
                prev = curr;
                curr = curr.next;
                prev.next = null;   // 最重要的一点
            }
        }
        return dummy.next;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy, next, start = head, end;
        while (true) {
            end = pre;
            for (int i = 0; i < k; i++) {
                end = end.next;
                if (end == null) {
                    return dummy.next;
                }
            }

            pre.next = null;
            next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;

            pre = start;
            start = next;
        }
    }

    private ListNode reverse(ListNode head) {
        ListNode newHead = null, curr = head, next;
        while (curr != null) {
            next = curr.next;
            curr.next = newHead;
            newHead = curr;
            curr = next;
        }
        return newHead;
    }

    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null) {
            return null;
        }

        ListNode slow = head, fast = head, prev = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = prev.next.next;
        return head;
    }


    // 1 <= left <= right <= n
    public ListNode reverseBetween222(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode pre = dummy, curr = head;
        for (int i = 1; i < left; i++) {
            pre = curr;
            curr = curr.next;
        }

        ListNode newTail = curr, newHead = null, next;
        for (int i = left; i <= right; i++) {
            next = curr.next;
            curr.next = newHead;
            newHead = curr;
            curr = next;
        }

        pre.next = newHead;
        newTail.next = curr;
        return dummy.next;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < k - 1; i++) {
            while (!deque.isEmpty() && nums[deque.peekFirst()] <= nums[i]) {
                deque.removeFirst();
            }
            deque.addFirst(i);
        }

        int n = nums.length, m = n - k + 1;
        int[] res = new int[m];
        for (int i = k - 1; i < n; i++) {
            while (!deque.isEmpty() && deque.peekLast() <= i - k) {
                deque.removeLast();
            }
            while (!deque.isEmpty() && nums[deque.peekFirst()] <= nums[i]) {
                deque.removeFirst();
            }
            deque.addFirst(i);
            res[i - k + 1] = nums[deque.peekLast()];
        }
        return res;
    }

    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        Deque<int[]> deque = new LinkedList<>();
        int currMax;
        for (int i = 0; i < n; i++) {
            // 计算当前最大值
            currMax = deque.isEmpty() ? nums[i] : nums[i] + deque.peekLast()[1];
            // 移除窗口外的值
            while (!deque.isEmpty() && deque.peekLast()[0] <= i - k) {
                deque.removeLast();
            }
            // 维持单调递减的队列
            while (!deque.isEmpty() && deque.peekFirst()[1] <= currMax) {
                deque.removeFirst();
            }
            deque.addFirst(new int[]{i, currMax});
        }
        return deque.peekFirst()[1];
    }

    public int magicTower(int[] nums) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int cnt = 0;
        long currSum = 1, totalSum = 1;
        for (int num : nums) {
            currSum += num;
            totalSum += num;
            priorityQueue.add(num);
            if (currSum <= 0) {
                while (currSum <= 0 && !priorityQueue.isEmpty() && priorityQueue.peek() < 0) {
                    currSum -= priorityQueue.remove();
                    cnt++;
                }
                if (currSum <= 0) {
                    return -1;
                }
            }
        }
        if (totalSum <= 0) {
            return -1;
        }
        return cnt;
    }

    public static void main(String[] args) {
        LeetCode_0021_MergeTwoSortedLists obj = new LeetCode_0021_MergeTwoSortedLists();
        obj.magicTower(new int[]{-200, -300, 400, 0});
    }
}
