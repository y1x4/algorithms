public class LeetCode_0141_LinkedListCycle {

    /**
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both list1 and list2 are sorted in non-decreasing order.
     *
     * @see LeetCode_0023_MergeKSortedLists
     * 
     * Time:  O(N)
     *      no circle: N
     *      has circle: move every time, distance between fast and slow pointers reduce 1
     * Space: O(1)
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;

        ListNode slow = head, fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null)
                return false;
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public static void main(String[] args) {
        LeetCode_0141_LinkedListCycle obj = new LeetCode_0141_LinkedListCycle();
    }
}
