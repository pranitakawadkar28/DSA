class Solution {
    int[][] dp;
    int[] suffix;
    int n;
    public int stoneGameII(int[] piles) {
        n = piles.length;

        dp = new int[n][n + 1];
        suffix = new int[n + 1];

        // Suffix sum
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        return solve(0, 1);
    }

    private int solve(int i, int M) {

        // All remaining piles can be taken
        if (i >= n) {
            return 0;
        }

        if (2 * M >= n - i) {
            return suffix[i];
        }

        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        int maxStones = 0;

        // Take X piles
        for (int X = 1; X <= 2 * M && i + X <= n; X++) {

            int newM = Math.max(M, X);

            // Current stones =
            // remaining stones - opponent's maximum
            int currentStones =
                    suffix[i] - solve(i + X, newM);

            maxStones = Math.max(maxStones, currentStones);
        }

        return dp[i][M] = maxStones;
    }
}