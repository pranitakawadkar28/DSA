class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] ans = new char[n];

        // Try to match target from left to right.
        for (int i = 0; i < n; i++) {
            int t = target.charAt(i) - 'a';

            // We can match target[i].
            if (freq[t] > 0) {
                ans[i] = target.charAt(i);
                freq[t]--;
                continue;
            }

            // Cannot match target[i].
            // Try to make the answer greater at position i.
            for (int c = t + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    ans[i] = (char) ('a' + c);
                    freq[c]--;

                    fillSmallest(ans, i + 1, freq);

                    return new String(ans);
                }
            }

            // Cannot make it greater at i.
            // Backtrack to an earlier position.
            return backtrack(ans, i - 1, freq, target);
        }

        // We matched target exactly.
        // Need to backtrack to make it strictly greater.
        return backtrack(ans, n - 1, freq, target);
    }

    private String backtrack(
            char[] ans,
            int pos,
            int[] freq,
            String target) {

        for (int i = pos; i >= 0; i--) {

            // Return the character used at ans[i]
            // back to the available pool.
            int used = ans[i] - 'a';
            freq[used]++;

            int t = target.charAt(i) - 'a';

            // Find the smallest character greater than target[i].
            for (int c = t + 1; c < 26; c++) {
                if (freq[c] > 0) {

                    ans[i] = (char) ('a' + c);
                    freq[c]--;

                    // Once we're greater, make the suffix
                    // as small as possible.
                    fillSmallest(ans, i + 1, freq);

                    return new String(ans);
                }
            }
        }

        return "";
    }

    private void fillSmallest(char[] ans, int start, int[] freq) {
        int idx = start;

        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans[idx++] = (char) ('a' + c);
                freq[c]--;
            }
        }
    }
}