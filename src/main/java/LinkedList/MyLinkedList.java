package LinkedList;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 设计链表
 */
public class MyLinkedList {
    private ListNode head;
    private ListNode tail;
    private int len;

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtTail(3);
        linkedList.addAtIndex(1, 2);   //链表变为1-> 2-> 3
        System.out.println(linkedList.get(1));            //返回2
        linkedList.deleteAtIndex(0);  //现在链表是1-> 3
        System.out.println(linkedList.get(1));            //返回3
    }

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
        head = tail = new ListNode(0);
        head.next = tail;
        tail.pre = head;
        len = 0;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index >= len || index < 0) return -1;
        ListNode begin = this.head;
        if (index == 0) return head.val;
        while (index > 0) {
            begin = begin.next;
            index--;
        }
        return begin.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        addAtIndex(len - 1, val);
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (len == 0 && index == 0) {
            ListNode insertNode = new ListNode(val);
            head = insertNode;
            head.next = tail;
            tail.pre = head;
            len++;
            return;
        }
        if (index < 0 || index >= len) return;
        ListNode node = head;
        index -= 1;
        while (index > 0) {
            index--;
            node = node.next;
        }
        ListNode insertNode = new ListNode(val);
        ListNode old = node.next;
        node.next = insertNode;
        insertNode.pre = node;
        insertNode.next = old;
        if (old != null) {
            old.pre = insertNode;
        }
        len++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= len) return;
        if (index == 0) {
            head = head.next;
            len--;
            return;
        }
        ListNode begin = head;
        while (index > 0) {
            index--;
            begin = begin.next;
        }
        if (begin.pre != null) {
            begin.pre.next = begin.next;
        }
        if (begin.next != null) {
            begin.next.pre = begin.pre;
        }
        len--;
    }

    static class ListNode {
        int val;
        ListNode pre;
        ListNode next;

        public ListNode(int value) {
            val = value;
            pre = next = null;
        }
    }
}
