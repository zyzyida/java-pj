package com.zyz.Interview.carl;

import java.util.ArrayList;
import java.util.List;

public class backtrack {
    public static void main(String[] args) {
        backtrack backtrack = new backtrack();
        backtrack.combine(4, 2);
    }

    //第77题. 组合
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> tmp = new ArrayList<>();
        backtracking(n, k, 1, result, tmp);
        return result;
    }

    private void backtracking(int n, int k, int startIndex, List<List<Integer>> result, List<Integer> tmp) {
        if (tmp.size() == k) {
            result.add(tmp);
            return;
        }
        for (int i = startIndex; i <= n; i++) {
            tmp.add(i);
            backtracking(n, k, i + 1, result, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }
}
