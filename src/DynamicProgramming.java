import java.util.*;
public class DynamicProgramming {
    // Return the number of unique paths a robot can take from cell (0,0) to cell (m - 1, n - 1) on an
    // m x n grid by only moving rightward or downward

    //Top down (DFS + memoization) solution.
    public static int uniquePaths(int m, int n) {
        int[][] memo = new int[m][n];
        return uniquePathsHelper(m - 1, n - 1, memo);
    }

    public static int uniquePathsHelper(int m, int n, int[][] memo) {
        if (m == 0 || n == 0) return 1;
        if (memo[m][n] != 0) return memo[m][n];
        memo[m][n] = uniquePathsHelper(m - 1, n, memo) + uniquePathsHelper(m, n - 1, memo);
        return memo[m][n];
    }

    public static int uniquePathsBottomUp(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // Return the minimum sum of all paths from the top left of grid to the bottom right where you can only go
    // down or right from a given cell.
    public static int minPathSum(List<List<Integer>> grid) {
        int[][] memo = new int[grid.size()][grid.get(0).size()];
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                memo[i][j] = -1;
            }
        }
        return minPathSumTopDown(grid, grid.size() - 1, grid.get(0).size() - 1, memo);
    }

    public static int minPathSumTopDown(List<List<Integer>> grid, int r, int c, int[][] memo) {
        if (r == 0 && c == 0) {
            return grid.get(r).get(c);
        }

        if (memo[r][c] != -1) {
            return memo[r][c];
        }

        if (r == 0) {
            memo[r][c] = minPathSumTopDown(grid, r, c - 1, memo) + grid.get(r).get(c);
            return memo[r][c];
        }

        if (c == 0) {
            memo[r][c] = minPathSumTopDown(grid, r - 1, c, memo) + grid.get(r).get(c);
            return memo[r][c];
        }

        memo[r][c] = Math.min(minPathSumTopDown(grid, r - 1, c, memo) + grid.get(r).get(c), minPathSumTopDown(grid, r, c - 1, memo) + grid.get(r).get(c));

        return memo[r][c];
    }

    public static int minPathSumBottomUp (List<List<Integer>> grid) {
        int m = grid.size(); // rows
        int n = grid.get(0).size(); // columns
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid.get(i).get(j);
                } else if (i == 0) {
                    dp[i][j] = grid.get(i).get(j) + dp[i][j - 1];
                } else if (j == 0) {
                    dp[i][j] = grid.get(i).get(j) + dp[i - 1][j];
                } else {
                    int cur = grid.get(i).get(j);
                    dp[i][j] = Math.min(dp[i - 1][j] + cur, dp[i][j - 1] + cur);
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // TRIANGLE +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Return the minimum sum of any path from the top of a triangle to the bottom of the triangle.
    // This solution uses DFS+memoization. Each recursive call considers the minimum path from the
    // top to this element.
    public static int minimumTotal1(List<List<Integer>> triangle) {
        int min = Integer.MAX_VALUE;
        int[][] memo = new int[triangle.size()][triangle.get(triangle.size() -1).size()];
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[i].length; j++) {
                memo[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < triangle.get(triangle.size() - 1).size(); i++) {
            int minPath = minimumTotal1Helper(triangle, triangle.size() - 1, i, memo);
            if (minPath < min) min = minPath;
        }

        return min;
    }

    public static int minimumTotal1Helper(List<List<Integer>> triangle, int row, int col, int[][] memo) {
        if (col < 0 || col > triangle.get(row).size() - 1) return Integer.MAX_VALUE;
        if (row == 0) return triangle.get(0).get(0);
        if (memo[row][col] != Integer.MAX_VALUE) return memo[row][col];

        int left = minimumTotal1Helper(triangle, row - 1, col - 1, memo);
        int right = minimumTotal1Helper(triangle, row - 1, col, memo);
        memo[row][col] = Math.min(left,right) + triangle.get(row).get(col);

        return memo[row][col];
    }

    // Bottom up approach where dp[i][j] is the minimum sum path from the top to i,j.
    // These solutions could be made a little better by making the subproblem for i,j
    // correspond to the maximum sum from i,j to the bottom of the pyramid. Then you wouldn't
    // have to compute the minimum over all elements in the last row.
    public static int minimumTotal2(List<List<Integer>> triangle) {
        int[][] dp = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];

        for (int i = 0; i < triangle.size(); i++) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                if (i == 0) {
                    dp[i][j] = triangle.get(i).get(j);
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + triangle.get(i).get(j);
                } else if (j == triangle.get(i).size() - 1) {
                    dp[i][j] = dp[i - 1][j - 1] + triangle.get(i).get(j);
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
                }
            }
        }

        int min = dp[dp.length - 1][0];

        for (int i = 0; i < triangle.get(triangle.size() - 1).size(); i++) {
            if (dp[dp.length - 1][i] < min ) min = dp[dp.length - 1][i];
        }

        return min;
    }

    // Return the length of the longest increasing subsequence
    public static int longestSubLen(List<Integer> nums) {
        if (nums.size() == 0) return 0;
        int[] memo = new int[nums.size()];

        int max = 1;
        for (int i = 0; i < nums.size(); i++) {
            int lis = longestSubLenHelper(nums, i, memo);
            max = Math.max(max, lis);
        }

        return max;
    }

    public static int longestSubLenHelper(List<Integer> nums, int i, int[] memo) {
        if (i == 0) return 1;
        if (memo[i] != 0) return memo[i];

        int max = 1;
        for (int j = 0; j < i; j++) {
            if (nums.get(j) < nums.get(i)) {
                int cur = longestSubLenHelper(nums, j, memo) + 1;
                if (cur > max) max = cur;
            }
        }

        memo[i] = max;
        return max;
    }
}
