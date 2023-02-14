package com.zyz.Interview.leetcode;

import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

public class practice {

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

    //1.两数之和：hashMap
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

    //2.两数相加：链表遍历
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry; // 执行加法运算
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry); // 最后一位进位
        }
        return head;
    }

    //3.无重复字符的最长子串：哈希集合
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> characters = new HashSet<>(); // 哈希集合，记录每个字符是否出现过
        int rk = -1, result = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (i != 0) {
                characters.remove(s.charAt(i - 1)); // 左指针向右移动一格，移除一个字符
            }
            while (rk + 1 < len && !characters.contains(s.charAt(rk + 1))) {
                characters.add(s.charAt(rk + 1));
                rk++;
            }
            result = Math.max(result, rk - i + 1);
        }
        return result;
    }

    //4.寻找两个正序数组的中位数：找第K大的数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        int totalLen = len1 + len2;
        if (totalLen % 2 == 1) {
            int midIndex = totalLen / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1); // 找到两个数组第k大的那个数，即为结果
            return median;
        } else {
            int midIndex1 = totalLen / 2 - 1, midIndex2 = totalLen / 2;
            double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
            return median;
        }
    }

    private double getKthElement(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length, len2 = nums2.length;
        int index1 = 0, index2 = 0;

        while (true) {
            // 边界情况
            if (index1 == len1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == len2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) { // 获取两个数组中，最小的那个数，即为第k大的数
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, len1) - 1; // 从两个数组的低位开始进行比较
            int newIndex2 = Math.min(index2 + half, len2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= newIndex1 - index1 + 1;
                index1 = newIndex1 + 1;
            } else {
                k -= newIndex2 - index2 + 1;
                index2 = newIndex2 + 1;
            }

        }
    }

    //5.最长回文子串：动态规划
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int len = s.length();
        int maxStart = 0, maxEnd = 0, maxLen = 1;
        boolean[][] dp = new boolean[len][len];

        for (int right = 1; right < len; right++) {
            for (int left = 0; left < right; left++) {
                if (s.charAt(left) == s.charAt(right) && (right - left <= 2 || dp[left + 1][right - 1])) {
                    dp[left][right] = true;
                    if (right - left + 1 > maxLen) {
                        maxLen = right - left + 1;
                        maxEnd = right;
                        maxStart = left;
                    }
                }
            }
        }
        return s.substring(maxStart, maxEnd + 1);
    }

    //7.整数反转
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int tmp = x % 10;
            if (res > 214748364 || (res == 214748364 && tmp > 7)) {
                return 0;
            }
            if (res < -214748364 || (res == -214748364 && tmp < -8)) {
                return 0;
            }
            res = res * 10 + tmp;
            x /= 10;
        }
        return res;
    }

    //8.字符串转换整数（atoi）
    public int myAtoi(String s) {
        int sign = 1;
        int res = 0;
        int m = s.length();
        int i = 0;
        while (i < m && s.charAt(i) == ' ') {
            i++;
        }
        int start = i;
        for (; i < m; i++) {
            char c = s.charAt(i);
            if (i == start && c == '+') {
                sign = 1;
            } else if (i == start && c == '-') {
                sign = -1;
            } else if (Character.isDigit(c)) {
                int num = c - '0';
                if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && num > Integer.MAX_VALUE % 10)) {
                    return Integer.MAX_VALUE;
                }
                if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && -num < Integer.MIN_VALUE % 10)) {
                    return Integer.MIN_VALUE;
                }
                res = res * 10 + sign * num;
            } else {
                break;
            }
        }
        return res;
    }

    //9.回文数
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        int cur = 0;
        int num = x;
        while (num > 0) {
            cur = cur * 10 + num % 10;
            num /= 10;
        }
        return cur == x;
    }

    //11.盛最多水的容器：动态规划
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int ans = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            ans = Math.max(ans, area);
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }

    //13，roman-to-integer
    public int romanToInt(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    //14.最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int length = strs[0].length();
        int count = strs.length;
        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < count; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    //15.三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        Arrays.sort(nums);//数组先排序
        ArrayList<List<Integer>> ans = new ArrayList<>();
        //枚举第一个数字a
        for (int first = 0; first < len; first++) {
            // a 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c对应的指针初始化指向数组的最右端
            int third = len - 1;
            int target = -nums[first];
            // 枚举b
            for (int second = first + 1; second < len; second++) {
                //需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                //需要保证b的指针在c的指针的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }
                // 如果指针重合，随着b后续的增加就不会有满足a+b+c=0并且b<c的c了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    //17.电话号码的字母组合：字符串、回溯
    public List<String> letterCombinations(String digits) {
        List<String> combination = new ArrayList<>();
        if (digits.length() == 0) {
            return combination;
        }
        HashMap<Character, String> hashMap = new HashMap<Character, String>() {
            {
                put('2', "abc");
                put('3', "def");
                put('4', "ghi");
                put('5', "jkl");
                put('6', "mno");
                put('7', "pqrs");
                put('8', "tuv");
                put('9', "wxyz");
            }
        };
        backTrack(combination, hashMap, digits, 0, new StringBuffer());
        return combination;
    }

    private void backTrack(List<String> combinations, HashMap<Character, String> phoneMap, String digits,
                           int index, StringBuffer combination) {
        if (index == digits.length()) { // 回溯结束的条件
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int letterCount = letters.length();
            for (int i = 0; i < letterCount; i++) {
                combination.append(letters.charAt(i));
                backTrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index); // 删除上一次回溯的元素
            }
        }
    }

    //19. 删除链表的倒数第 N 个结点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode temp = new ListNode(0, head);
        ListNode first = head, second = temp;
        for (int i = 0; i < n; i++) { // 先走n步
            first = first.next;
        }
        while (first != null) { // 再一起走到最后
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        ListNode ans = temp.next;
        return ans;
    }

    //20.有效的括号
    public boolean isValid(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }
        HashMap<Character, Character> pairMap = new HashMap<Character, Character>() {
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        };
        Deque<Character> deque = new LinkedList<>(); // Deque:双端队列，Queue:队列，Stack:栈
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pairMap.containsKey(ch)) {
                if (deque.isEmpty() || deque.peek() != pairMap.get(ch)) { // peek():获取队头元素
                    return false;
                }
                deque.pop(); // 删除队头
            } else {
                deque.push(ch); // 在队头插入元素
            }
        }
        return deque.isEmpty();
    }

    //21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }

        ListNode tmp1 = list1, tmp2 = list2;
        ListNode dummy = new ListNode(0);
        ListNode res = dummy;

        while (tmp1 != null && tmp2 != null) {
            if (tmp1.val <= tmp2.val) {
                res.next = tmp1;
                tmp1 = tmp1.next;
            } else {
                res.next = tmp2;
                tmp2 = tmp2.next;
            }
            res = res.next;
        }
        res.next = tmp1 == null ? tmp2 : tmp1;
        return dummy.next;
    }

    //22. 括号生成，回溯法
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    private void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }
        //如果左括号数量不大于n,我们可以放一个左括号
        if (open < max) {
            cur.append('(');
            backtrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1); // 删除上一次回溯的结果
        }
        //如果右括号数量小于左括号的数量，我们可以放一个右括号
        if (close < open) {
            cur.append(')');
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }

    //23. 合并K个升序链表
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        int k = lists.length;
        while (k > 1) {
            int index = 0;
            for (int i = 0; i < k; i += 2) {
                if (i == k - 1) {
                    lists[index++] = lists[i];//最后一个链表
                } else {
                    lists[index++] = mergeTwoLists(lists[i], lists[i + 1]);//合并两个有序链表
                }
            }
            k = index;
        }
        return lists[0];
    }

    //31. 下一个排列
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) { // [4,5,2,6,3,1]，从后往前找，找到第一个后一个元素大于前一个元素的那个index，i=2
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) { //找到index之后第一个大于index元素的的那个index2，j=4
                j--;
            }
            swap(nums, i, j); // 交换后，[4,5,3,6,2,1]
        }
        reverse(nums, i + 1); // 降序变升序，[4,5,3,1,2,6]
    }

    private void reverse(int[] nums, int i) {
        int left = i, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //32. 最长有效括号，栈/动态规划
    public int longestValidParentheses(String s) {
        int maxAns = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxAns = Math.max(maxAns, i - stack.peek());
                }
            }
        }
        return maxAns;
    }

    //33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }

        return -1;
    }

    //34. 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        int leftIndex = binarySearch(nums, target, true);// 第一个等于 target 的位置
        int rightIndex = binarySearch(nums, target, false) - 1;// 第一个大于 target 的位置减1

        if (leftIndex <= rightIndex && rightIndex < nums.length && nums[leftIndex] == target && nums[rightIndex] == target) {
            return new int[]{leftIndex, rightIndex};
        }
        return new int[]{-1, -1};
    }

    private int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    //39. 组合总和
    public List<List<Integer>> combinationSum(int[] candidates, int target) { //[2,3,6,7],7
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }
        Deque<Integer> path = new ArrayDeque<>(); //Deque:双端队列
        dfs(candidates, 0, len, target, path, res);

        return res;
    }

    //深度优先搜索DFS、广度优先搜索BFS
    private void dfs(int[] candidates, int begin, int len, int target, Deque<
            Integer> path, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < len; i++) {
            path.addLast(candidates[i]);
            dfs(candidates, i, len, target - candidates[i], path, res);
            path.removeLast();
        }
    }

    //N皇后问题

    //46. 全排列
    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }
        boolean[] used = new boolean[len];
        List<Integer> path = new ArrayList<>();
        dfs(nums, len, 0, path, used, res);
        return res;
    }

    public void dfs(int[] nums, int len, int depth, List<Integer> path, boolean[] used, List<List<Integer>> res) {
        // 所有数据都填完了
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 在非叶子结点处，产生不同的分支，这一操作的语义是：在还未选择的数中依次选择一个元素作为下一个位置的元素，这显然得通过一个循环实现。
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;

                //System.out.println("  递归之前 => " + path);
                dfs(nums, len, depth + 1, path, used, res);

                // 注意：下面这两行代码发生 「回溯」，回溯发生在从 深层结点 回到 浅层结点 的过程，代码在形式上和递归之前是对称的
                used[i] = false;
                path.remove(path.size() - 1);
                //System.out.println("递归之后 => " + path);
            }
        }
    }

    //79.单词搜索
    public boolean exist(char[][] board, String word) {
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, 1, 0, -1};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (dfs(board, word, dx, dy, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, String word, int[] dx, int[] dy, int u, int x, int y) {
        // 递归边界1：如果没有匹配到word第u个字符，直接退出，不符题意
        if (board[x][y] != word.charAt(u)) {
            return false;
        }
        // 递归边界2：搜索到单词末尾，完成目标，返回ture
        if (u == word.length() - 1) {
            return true;
        }
        char char_temp = board[x][y];
        board[x][y] = '.';// 这里随意，保证前后一致即可，主要是改成特殊字母避免重复搜索

        for (int i = 0; i < 4; i++) {
            int a = x + dx[i];
            int b = y + dy[i];
            // 当到达搜索边界或者找到了已经搜过的字符，换一个位置重新搜
            if (a < 0 || a >= board.length || b < 0 || b >= board[0].length || board[a][b] == '.') {
                continue;
            }
            if (dfs(board, word, dx, dy, u + 1, a, b)) {
                return true;
            }
        }

        //回溯，将填充的.变回去
        board[x][y] = char_temp;
        return false;
    }

    //205.同构字符串
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> s2t = new HashMap<Character, Character>();
        Map<Character, Character> t2s = new HashMap<Character, Character>();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char x = s.charAt(i), y = t.charAt(i);
            if ((s2t.containsKey(x) && s2t.get(x) != y) || (t2s.containsKey(y) && t2s.get(y) != x)) {
                return false;
            }
            s2t.put(x, y);
            t2s.put(y, x);
        }
        return true;
    }

    //347.前k个高频元素
    public int[] topKFrequent(int[] nums, int k) {
        //1.统计频率
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        //2.构造List数组
        List<int[]> values = new ArrayList<int[]>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey(), count = entry.getValue();
            values.add(new int[]{num, count});
        }
        //3.快排

        return new int[]{};
    }

    //1143.最长公共子序列
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        if (m == 0 || n == 0) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            char c1 = text1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = text2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    //打印杨辉三角
    public static void printYangHuiTriangle(int n) {
        int[][] triangle = new int[n][n];
        for (int i = 0; i < n; i++) {
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.printf(triangle[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 排序专题
     * https://blog.csdn.net/y3over/article/details/86145291
     *
     * @param
     * @return
     */
    //1.冒泡排序
    // 原理：把最大的换到最后一位，然后把第一大的换到倒数第二位
    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        while (len > 0) {
            for (int i = 0; i < len - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    int tmp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = tmp;
                }
            }
            len--;
        }
    }

    //2.快速排序
    // 原理：从数列中取出一个值，将比这个值大的放在它的右边，将比这个值小的放在它的左边，再最左右两个区域重复这个过程，直到各个区域只有一个数。
    public void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = arr[left];// 基准点
        int lo = left;
        int hi = right;
        while (lo < hi) {

            while (arr[hi] >= pivot && lo < hi) {
                hi--;
            }
            //把右边受影响元素和左边换
            arr[lo] = arr[hi];

            while (arr[lo] <= pivot && lo < hi) {
                lo++;
            }
            //把左边受影响元素和右边换
            arr[hi] = arr[lo];
        }
        //把拿出来的元素放回去
        arr[lo] = pivot;

        quickSort(arr, left, lo - 1);
        quickSort(arr, lo + 1, right);
    }

    //3.选择排序
    // 原理：找出数组中的最小值，然后与第一位交换，然后再找出第二小的值与第二位交换
    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            arr[minIndex] = arr[i];
            arr[i] = min;
        }
    }

    //4.插入排序
    // 原理：对于未排序数据，在已排序序列中从后向前扫描（可采用顺序查找，折半（二分）查找），找到相应位置并插入。
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            int insertVal = arr[i];
            int insertIndex = i - 1;
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            arr[insertIndex + 1] = insertVal;
        }
    }

    //5.堆排序
    // 原理：
    //    堆是一棵顺序存储的完全二叉树。
    //    其中每个结点的关键字都不大于其孩子结点的关键字，这样的堆称为小根堆。
    //    其中每个结点的关键字都不小于其孩子结点的关键字，这样的堆称为大根堆。
    //    把数组组成最大堆，从数组中取走第一个，再次组成新的最大堆
    // 步骤：1.把无序数组构建成二叉树。2.循环删除堆顶元素，移到集合尾部，调解堆产生新的堆顶。
    public static void sort(int[] arr) {
        //1.第一次构建大顶堆，从后往前依次调整
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length); //从第一个非叶子结点从下至上，从右至左调整结构
        }
        //每次将根和待排序的最后一个交换，然后再调整
        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            tmp = arr[0];
            arr[0] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = tmp;
            adjustHeap(arr, 0, arr.length - 1 - i - 1);
        }
    }

    private static void adjustHeap(int[] arr, int i, int length) {
    }

    //二叉树的前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                res.add(node.val);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node = node.right;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("==================================");
        practice practice = new practice();
        System.out.println(practice.lengthOfLongestSubstring("abcacbd"));
        practice.findMedianSortedArrays(new int[]{1, 3}, new int[]{2, 4, 5, 6});
        practice.threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        practice.letterCombinations("23");
        practice.isValid("([)]");
        practice.generateParenthesis(2);
        practice.longestValidParentheses(")()())");
        practice.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        List<List<Integer>> lists = practice.combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println(lists);
        printYangHuiTriangle(10);
        System.out.println(practice.permute(new int[]{1, 2, 3}));
        System.out.println(practice.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED"));

        int[] nums = {7, 4, 3, 8, 16, 10};
        practice.quickSort(nums, 0, 5);
        for (int num : nums) {
            System.out.println(num);
        }

    }
}
