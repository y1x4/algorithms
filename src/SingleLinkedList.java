import java.util.HashMap;
import java.util.Map;

/**
 * @author yixu
 * @date 2020-08-27 22:04:09
 */
public class SingleLinkedList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 迭代：时间复杂度O(N)，空间复杂度O(1)；
     * 递归：时间复杂度O(N)，空间复杂度O(N)。
     */
    public static ListNode reverseListLoop(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode curr = head, next, newHead = null;
        while (curr != null) {
            next = curr.next;
            curr.next = newHead;
            newHead = curr;
            curr = next;
        }
        return newHead;
    }

    public static ListNode reverseListRecursively(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode newHead = reverseListLoop(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 一个链表，奇数位置升序、偶数位置降序，将其重新排序
     * 思路：
     * 1.拆分成两个链表
     * 2.反转降序链表
     * 3.合并两个有序链表
     */
    public static ListNode sortUpAndDownLists(ListNode head) {
        if (head == null || head.next == null)
            return head;

        // 1.拆
        ListNode evenHead = head.next;
        ListNode oddCurr = head, evenCurr = evenHead;
        while (evenCurr != null && evenCurr.next != null) {
            oddCurr.next = evenCurr.next;
            oddCurr = oddCurr.next;
            evenCurr.next = oddCurr.next;
            evenCurr = evenCurr.next;
        }
        oddCurr.next = null;

        // 2.反转
        evenHead = reverseListLoop(evenHead);

        // 3.合并
        return mergeLists(head, evenHead);
    }

    public static ListNode mergeLists(ListNode head1, ListNode head2) {
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        ListNode dummy = new ListNode(-1), curr = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                curr.next = head1;
                head1 = head1.next;
            } else {
                curr.next = head2;
                head2 = head2.next;
            }

            curr = curr.next;
        }

        if (head1 != null) {
            curr.next = head1;
        }
        if (head2 != null) {
            curr.next = head2;
        }

        return dummy.next;
    }


    /**
     * LeetCode 1171. 从链表中删去总和值为零的连续节点
     */
    public ListNode removeZeroSumSublists(ListNode head) {
        while (head != null && head.val == 0) {
            head = head.next;
        }
        if (head == null || head.next == null)
            return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        Map<Integer, ListNode> preSumMap = new HashMap<>();
        int preSum = 0;
        ListNode curr = dummy;
        while (curr != null) {
            preSum += curr.val;

            // 如果存在前缀和，删除这段链表及其间的前缀和
            // 否则，存入<前缀和，节点>
            if (preSumMap.containsKey(preSum)) {
                int sum = preSum;
                ListNode node = preSumMap.get(preSum).next;
                while (node != curr) {
                    sum += node.val;
                    preSumMap.remove(sum);
                    node = node.next;
                }
                preSumMap.get(preSum).next = curr.next;
            } else {
                preSumMap.put(preSum, curr);
            }

            curr = curr.next;
        }
        return dummy.next;
    }

    public ListNode revs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = revs(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
