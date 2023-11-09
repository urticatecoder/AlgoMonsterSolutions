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
}
