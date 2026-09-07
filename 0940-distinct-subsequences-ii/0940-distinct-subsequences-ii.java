class Solution {
    public int distinctSubseqII(String s) {
        long MOD = 1_000_000_007L;

        long[] dp = new long[26];
        long total = 0;

        for (char c : s.toCharArray()) {
            int idx = c - 'a';

            long newSubsequences = (total + 1) % MOD;

            // Replace subsequences ending with this character.
            total = (total + newSubsequences - dp[idx] + MOD) % MOD;

            dp[idx] = newSubsequences;
        }

        return (int) total;
    }
}