package com.zyz.Interview.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class offer {

    public class ListNode {
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

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //剑指 Offer 05. 替换空格
    public String replaceSpace(String s) {
        int length = s.length();
        char[] array = new char[length * 3];
        int size = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                array[size++] = '%';
                array[size++] = '2';
                array[size++] = '0';
            } else {
                array[size++] = c;
            }
        }
        return new String(array, 0, size);
    }

    //剑指 Offer 06. 从尾到头打印链表
    public int[] reversePrint(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;
        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        int size = stack.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = stack.pop().val;
        }
        return result;
    }

    //剑指 Offer 07. 重建二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(0, 0, inorder.length - 1, preorder, inorder);
    }

    public TreeNode helper(int preStart, int inStart, int inEnd, int[] preOrder, int[] inOrder) {
        if (preStart > preOrder.length - 1 || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[preStart]);
        int index = 0;
        for (int i = inStart; i < inEnd; i++) {
            if (inOrder[i] == root.val) {
                index = i;
                break;
            }
        }
        root.left = helper(preStart + 1, inStart, index - 1, preOrder, inOrder);
        root.right = helper(preStart + index - inStart + 1, index + 1, inEnd, preOrder, inOrder);
        return root;
    }

    //剑指 Offer 09. 用两个栈实现队列
    class CQueue {
        //两个栈，一个出栈，一个入栈
        private LinkedList<Integer> inStack;
        private LinkedList<Integer> outStack;

        public CQueue() {
            inStack = new LinkedList<Integer>();
            outStack = new LinkedList<Integer>();
        }

        public void appendTail(int value) {
            inStack.push(value);
        }

        public int deleteHead() {
            if (outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
            if (outStack.isEmpty()) {
                return -1;
            } else {
                return outStack.pop();
            }
        }
    }

    //剑指 Offer 40. 最小的K个数
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        return quickSearch(arr, 0, arr.length - 1, k - 1);
    }

    private int[] quickSearch(int[] nums, int lo, int hi, int k) {
        //每快排一次，得到的j就是左边都小于它，右边都大于它。什么时候j==k了，说明找到前k个了
        int j = partition(nums, lo, hi);
        if (j == k) {
            return Arrays.copyOf(nums, j + 1);
        }
        return j > k ? quickSearch(nums, lo, j - 1, k) : quickSearch(nums, j + 1, hi, k);
    }

    private int partition(int[] nums, int lo, int hi) {
        int v=nums[lo];
        int i=lo,j=hi+1;
        while (true){
            // 从lo+1到hi，对应数值如果小于v，往后走（到从前往后第一个大于等于v的停下来）
            while (++i<=hi && nums[i]<v);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================");
        offer offer = new offer();
        int[] leastNumbers = offer.getLeastNumbers(new int[]{3, 6, 4, 8, 1}, 3);
        for (int num : leastNumbers) {
            System.out.println(num);
        }
    }

}
