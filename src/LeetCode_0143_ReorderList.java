public class LeetCode_0143_ReorderList {

    /**
     * Time:  O(N)
     * Space: O(1)
     */
    public void reorderList(ListNode head) {
        if (head.next == null) {
            return;
        }

        // 1. find list's median and split
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode curr = slow.next;
        slow.next = null;

        // 2. reverse right half list
        ListNode newHead = null, next;
        while (curr != null) {
            next = curr.next;
            curr.next = newHead;
            newHead = curr;
            curr = next;
        }

        // 3. merge 2 half list
        ListNode dummy = new ListNode(-1);
        curr = dummy;
        while (head != null) {
            curr.next = head;
            curr = curr.next;
            head = head.next;
            if (newHead != null) {
                curr.next = newHead;
                curr = curr.next;
                newHead = newHead.next;
            }
        }
        head = dummy.next;
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
        LeetCode_0143_ReorderList obj = new LeetCode_0143_ReorderList();
    }
}
