package LinkedList;

import java.util.HashSet;
import java.util.Set;

public class LinkedList {
    public static class ListNode {
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

    /**
     * 2. 两数相加
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        while (l1 == null || l2 == null) return null;
        ListNode sum = new ListNode(-1);
        ListNode res = sum;
        int jinwei = 0;
        while (l1 != null && l2 != null) {
            if (l1.val + l2.val + jinwei >= 10) {
                sum.next = new ListNode(l1.val + l2.val + jinwei - 10);
                jinwei = 1;
            } else {
                sum.next = new ListNode(l1.val + l2.val + jinwei);
                jinwei = 0;
            }
            l1 = l1.next;
            l2 = l2.next;
            sum = sum.next;
        }
        ListNode diff = l1 == null ? l2 : l1;
        while (diff != null) {
            if (diff.val + jinwei >= 10) {
                sum.next = new ListNode(diff.val + jinwei - 10);
                jinwei = 1;
            } else {
                sum.next = new ListNode(diff.val + jinwei);
                jinwei = 0;
            }
            sum = sum.next;
            diff = diff.next;
        }
        if (jinwei != 0) sum.next = new ListNode(jinwei);
        return res.next;
    }

    public ListNode mergeTwoListsRecur(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(-1);
        ListNode res = pre;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                res.next = l1;
                l1 = l1.next;
            } else {
                res.next = l2;
                l2 = l2.next;
            }
            res = res.next;
        }
        res.next = l1 == null ? l2 : l1;
        return pre.next;
    }

    /**
     * 142 环形链表
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
//        1.使用set保存所有listnode
//        if (head == null) return head;
//        Set<ListNode> set = new HashSet<>();
//        while (head != null) {
//            if (set.contains(head)) {
//                return head;
//            }
//            set.add(head);
//            head = head.next;
//        }
//        return null;

        if (head == null || head.next == null) return null;
        ListNode low = head, fast = head;
        while (fast != null) {
            low = low.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            //第一次相遇时
            if (fast == low) {
                ListNode pre = head;
                while (pre != low) {
                    low = low.next;
                    pre = pre.next;
                }
                return pre;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ListNode t1 = new ListNode(9);
        t1.next = new ListNode(9);

        ListNode t2 = new ListNode(9);
        t2.next = new ListNode(9);
        t2.next.next = new ListNode(9);
        ListNode res = addTwoNumbers(t1, t2);
//        System.out.println();
    }
}
