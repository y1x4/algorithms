public class LeetCode_0021_MergeTwoSortedLists {

    /**
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both list1 and list2 are sorted in non-decreasing order.
     *
     * @see LeetCode_0023_MergeKSortedLists
     *
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


    public static void main(String[] args) {
        LeetCode_0021_MergeTwoSortedLists obj = new LeetCode_0021_MergeTwoSortedLists();
    }
}
