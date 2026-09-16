class Solution {
    private static final int MOD = 1_000_000_007;


    public int numberOfSets(int n, int k) {
        int m = n + k - 1;
        int r = 2 * k;

        long[] dp = new long[r + 1];
        dp[0] = 1;

        for (int i = 1; i <= m; i++) {
            for (int j = Math.min(i, r); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }

        return (int) dp[r];
    }
}