public class LeetCode_0023_MergeKSortedLists {

    /**
     * k == lists.length
     * 0 <= k <= 104
     * 0 <= lists[i].length <= 500
     * -104 <= lists[i][j] <= 104
     * lists[i] is sorted in ascending order.
     * The sum of lists[i].length will not exceed 104.
     * <p>
     * Time:  O(kN*logk)
     * Space: O(logk)
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists(lists, 0, lists.length - 1);
    }

    private ListNode mergeKLists(ListNode[] lists, int l, int r) {
        if (l > r) return null;

        if (l == r) return lists[l];

        int mid = (l + r) >> 1;
        return mergeTwoLists(mergeKLists(lists, l, mid), mergeKLists(lists, mid + 1, r));
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
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
        LeetCode_0023_MergeKSortedLists obj = new LeetCode_0023_MergeKSortedLists();
    }
}
