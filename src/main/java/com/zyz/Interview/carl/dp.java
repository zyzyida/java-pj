package com.zyz.Interview.carl;

/**
 * 思路：动态规划中每一个状态一定是由上一个状态推导出来的，这一点就区分于贪心，贪心没 有状态推导，而是从局部直接选最优的。
 * 解题步骤：
 * 1. 确定dp数组(dp table)以及下标的含义
 * 2. 确定递推公式
 * 3. dp数组如何初始化
 * 4. 确定遍历顺序
 * 5. 举例推导dp数组
 */

public class dp {
    public static void main(String[] args) {
        System.out.println(ferbunaqie(4));
        System.out.println(climbStairs(4));
        System.out.println(uniquePathsWithObstacles(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}));
        System.out.println(integerBreak(5));
    }

    //1.斐波那契数列

    /**
     * 1. dp[i]的定义为:第i个数的斐波那契数值是dp[i]
     * 2. dp[i] = dp[i - 1] + dp[i - 2]
     * 3. dp[0] = 0; dp[1] = 1;
     * 4. 从递归公式dp[i] = dp[i - 1] + dp[i - 2];中可以看出，dp[i]是依赖 dp[i - 1] 和 dp[i - 2]，那么遍历的顺序一定是从前到后遍历的
     * 5. 0 1 1 2 3 5 8 13 21 34 55
     */
    public static int ferbunaqie(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    //2.爬楼梯

    /**
     * 1.dp[i]: 爬到第i层楼梯，有dp[i]种方法
     * 2.dp[i] = dp[i - 1] + dp[i - 2]
     * 3.dp[1]=1,dp[2]=2
     * 4.从递推公式dp[i] = dp[i - 1] + dp[i - 2];中可以看出，遍历顺序一定是从前向后遍历的
     * 5.1 2 3 5 8
     */
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    //爬楼梯2
    /**
     * 爬楼梯题目还可以继续深化，就是一步一个台阶，两个台阶，三个台阶，直到 m个台阶，有 多少种方法爬到n阶楼顶
     * 1.dp[i]: 爬到第i层楼梯，有dp[i]种方法
     * 2.
     */

    //3.使用最小花费爬楼梯

    /**
     * cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
     * 1.dp[i]的定义:到达第i个台阶所花费的最少体力为dp[i]（第一步是花费体力的，最后一步是不花费体力的）
     * 2.dp[i]=min(dp[i-1],dp[i-2])+cost[i]
     * 3.dp[0]=cost[0],dp[1]=cost[1]
     * 4.因为是模拟台阶，而且dp[i]又dp[i-1]dp[i-2]推出，所以是从前到后遍历cost数组就可以了。
     * 5. 1 100 2 3 3 103 4 5 104 6
     */
    public static int minCostClimbingStairs(int[] array) {
        int[] dp = new int[array.length];
        dp[0] = array[0];
        dp[1] = array[1];
        for (int i = 2; i < array.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + array[i];
        }
        return Math.min(dp[array.length - 1], dp[array.length - 2]);
    }

    /**
     * 1.dp[i]的定义:到达第i个台阶所花费的最少体力为dp[i]（第一步是不花费体力的，最后一步是花费体力的）
     * 2.dp[i]=min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2])
     * 3.dp[0]=0,dp[1]=0
     * 4.从前到后遍历cost数组
     * 5.
     */
    public static int minCostClimbingStairs2(int[] array) {
        int[] dp = new int[array.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= array.length; i++) {
            dp[i] = Math.min(dp[i - 1] + array[i - 1], dp[i - 2] + array[i - 2]);
        }
        return dp[array.length];
    }

    //4.不同路径

    /**
     * 1.dp[i][j] :表示从(0 ，0)出发，到(i, j) 有dp[i][j]条不同的路径。
     * 2.dp[i][j]=dp[i][j-1]+dp[i-1][j]
     * 3.dp[i][0] = 1,dp[0][j] = 1
     * 4.从左到右一层一层遍历就可以了
     * 5.
     */
    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 不同路径 II
     * 1.dp[i][j] :表示从(0 ，0)出发，到(i, j) 有dp[i][j]条不同的路径。
     * 2.if (obstacleGrid[i][j] == 0) {dp[i][j] = dp[i - 1][j] + dp[i][j - 1];}
     * 3.dp[i][0] = 1 && obstacleGrid[i][0] == 0,dp[0][j] = 1 && obstacleGrid[0][j] == 0
     * 4.一定是从左到右一层一层遍历
     * 5.
     */
    public static int uniquePathsWithObstacles(int[][] array) {
        int m = array.length;
        int n = array[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m && array[i][0] == 0; i++) dp[i][0] = 1;
        for (int j = 0; j < m && array[0][j] == 0; j++) dp[0][j] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (array[i][j] == 1) continue;
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    //5.整数拆分

    /**
     * 1.dp[i]:分拆数字i，可以得到的最大乘积为dp[i]。
     * 2.dp[i]=max(dp[i],max(j*dp[i-j],j*(i-j)))
     * 3.dp[2]=1
     * 4.遍历i一定是从前向后遍历，先有dp[i - j]再有dp[i]
     * 5. 1 2 4 6 12 18 27 36
     */
    public static int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j < i - 1; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * dp[i - j], j * (i - j)));
            }
        }
        return dp[n];
    }

}
