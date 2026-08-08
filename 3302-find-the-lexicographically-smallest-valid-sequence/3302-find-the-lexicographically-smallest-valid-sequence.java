class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        /*
         * exact[i] = rightmost possible index of word1[i]
         * when matching word2[i..m-1] exactly.
         *
         * one[i] = rightmost possible index of word1[i]
         * when matching word2[i..m-1] with at most one mismatch.
         */
        int[] exact = new int[m + 1];
        int[] one = new int[m + 1];

        Arrays.fill(exact, -1);
        Arrays.fill(one, -1);

        exact[m] = n;
        one[m] = n;

        // Positions of every character in word1.
        List<Integer>[] positions = new ArrayList[26];
        for (int c = 0; c < 26; c++) {
            positions[c] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            positions[word1.charAt(i) - 'a'].add(i);
        }

        /*
         * Build exact[] and one[] from right to left.
         */
        for (int i = m - 1; i >= 0; i--) {

            // Case 1: match word2[i] exactly.
            int bound = exact[i + 1];

            if (bound > 0) {
                int p = lastPositionBefore(
                        positions[word2.charAt(i) - 'a'],
                        bound
                );

                if (p != -1) {
                    exact[i] = p;
                }
            }

            /*
             * one[i]:
             *
             * A) Match current character exactly and use the
             *    mismatch somewhere in the remaining suffix.
             */
            bound = one[i + 1];

            if (bound > 0) {
                int p = lastPositionBefore(
                        positions[word2.charAt(i) - 'a'],
                        bound
                );

                if (p != -1) {
                    one[i] = Math.max(one[i], p);
                }
            }

            /*
             * B) Use the mismatch at the current character.
             *    Then the remaining suffix must match exactly.
             *
             * The rightmost available index before exact[i+1]
             * can be used, regardless of its character.
             */
            bound = exact[i + 1];

            if (bound > 0) {
                one[i] = Math.max(one[i], bound - 1);
            }
        }

        /*
         * Greedily construct the lexicographically smallest answer.
         */
        int[] ans = new int[m];

        int prev = -1;
        boolean usedMismatch = false;

        for (int i = 0; i < m; i++) {
            boolean found = false;

            for (int j = prev + 1; j < n; j++) {

                if (word1.charAt(j) == word2.charAt(i)) {
                    /*
                     * Current character matches.
                     *
                     * The remaining suffix may still use
                     * the one allowed mismatch.
                     */
                    if (one[i + 1] > j) {
                        ans[i] = j;
                        prev = j;
                        found = true;
                        break;
                    }
                } else if (!usedMismatch) {
                    /*
                     * Use our one mismatch here.
                     *
                     * Therefore the remaining suffix must
                     * match exactly.
                     */
                    if (exact[i + 1] > j) {
                        ans[i] = j;
                        prev = j;
                        usedMismatch = true;
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                return new int[0];
            }
        }

        return ans;
    }

    // Returns the largest value < bound.
    private int lastPositionBefore(List<Integer> list, int bound) {
        int lo = 0;
        int hi = list.size() - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (list.get(mid) < bound) {
                ans = list.get(mid);
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return ans;
    }
}