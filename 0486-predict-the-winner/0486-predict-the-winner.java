class Solution {
    private int[][] dp;

    private int solve(int i, int j, int[] nums) {
        if (i == j) {
            return nums[i];
        }

        if (dp[i][j] != Integer.MIN_VALUE) {
            return dp[i][j];
        }

        int takeLeft = nums[i] - solve(i + 1, j, nums);
        int takeRight = nums[j] - solve(i, j - 1, nums);

        dp[i][j] = Math.max(takeLeft, takeRight);
        return dp[i][j];
    }

    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }

        return solve(0, n - 1, nums) >= 0;
    }
}