package com.jerryxgh;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 */
public class Problem2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers(l1, l2, 0);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
        if (null == l1 && 0 == carry) {
            return l2;
        } else if (null == l2 && 0 == carry) {
            return l1;
        }

        int value = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
        carry = 0;
        if (value >= 10) {
            value -= 10;
            carry = 1;
        }

        ListNode currentNode = new ListNode();
        currentNode.val = value;
        currentNode.next = addTwoNumbers(l1 == null ? null : l1.next, l2 == null ? null : l2.next, carry);

        return currentNode;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}

