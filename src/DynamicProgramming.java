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
}
