public class LeetCode_82_RemoveDuplicatesFromSortedListII {

    /**
     * Time:  O(N)
     * Space: O(1)
     */
    public ListNode deleteDuplicates(ListNode head) {
        // 0. fast return
        if (head == null || head.next == null) {
            return head;
        }

        // while loop, check every unique value and connect them
        ListNode dummy = new ListNode(-1), curr = dummy;
        while (head != null) {
            if (head.next != null && head.val == head.next.val) {
                int val = head.val;
                while (head != null && head.val == val) {
                    head = head.next;
                }
            } else {
                curr.next = head;
                curr = curr.next;
                head = head.next;
            }
        }
        curr.next = null;   // Cut off previous reference
        return dummy.next;
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        LeetCode_82_RemoveDuplicatesFromSortedListII obj = new LeetCode_82_RemoveDuplicatesFromSortedListII();
    }
}
