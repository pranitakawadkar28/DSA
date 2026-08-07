class Solution {

    // Exponents of primes [2, 3, 5, 7] for digits 0..9
    private static final int[][] F = {
        {0, 0, 0, 0}, // 0
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    private int A, B, C, D;
    private int[] dp;

    private int id(int a, int b, int c, int d) {
        return (((a * (B + 1) + b) * (C + 1) + c) * (D + 1) + d);
    }

    /*
     * dp[a,b,c,d] =
     * minimum number of digits needed whose product contains
     * at least 2^a * 3^b * 5^c * 7^d.
     */
    private void buildDP() {
        int size = (A + 1) * (B + 1) * (C + 1) * (D + 1);

        dp = new int[size];
        Arrays.fill(dp, 1_000_000_000);

        dp[id(0, 0, 0, 0)] = 0;

        for (int a = 0; a <= A; a++) {
            for (int b = 0; b <= B; b++) {
                for (int c = 0; c <= C; c++) {
                    for (int d = 0; d <= D; d++) {

                        if (a == 0 && b == 0 && c == 0 && d == 0)
                            continue;

                        int best = Integer.MAX_VALUE;

                        for (int digit = 2; digit <= 9; digit++) {
                            int na = Math.max(0, a - F[digit][0]);
                            int nb = Math.max(0, b - F[digit][1]);
                            int nc = Math.max(0, c - F[digit][2]);
                            int nd = Math.max(0, d - F[digit][3]);

                            best = Math.min(
                                best,
                                dp[id(na, nb, nc, nd)] + 1
                            );
                        }

                        dp[id(a, b, c, d)] = best;
                    }
                }
            }
        }
    }

    private int get(int[] need) {
        return dp[id(
            need[0],
            need[1],
            need[2],
            need[3]
        )];
    }

    private void subtract(int[] need, int digit) {
        need[0] = Math.max(0, need[0] - F[digit][0]);
        need[1] = Math.max(0, need[1] - F[digit][1]);
        need[2] = Math.max(0, need[2] - F[digit][2]);
        need[3] = Math.max(0, need[3] - F[digit][3]);
    }

    private int[] copyAndSubtract(int[] need, int digit) {
        int[] res = need.clone();
        subtract(res, digit);
        return res;
    }

    /*
     * Construct the lexicographically smallest string of exactly len
     * digits satisfying 'need'.
     *
     * If dp[need] <= remaining positions, the unused positions can
     * simply be filled with 1's.
     */
    private String buildSmallest(int[] need, int len) {
        StringBuilder ans = new StringBuilder(len);

        for (int pos = 0; pos < len; pos++) {
            int remaining = len - pos - 1;

            for (int digit = 1; digit <= 9; digit++) {
                int[] next = copyAndSubtract(need, digit);

                if (get(next) <= remaining) {
                    ans.append((char) ('0' + digit));
                    need = next;
                    break;
                }
            }
        }

        return ans.toString();
    }

    public String smallestNumber(String num, long t) {

        /*
         * Factor t.
         *
         * A digit product can only contain prime factors
         * 2, 3, 5, 7.
         */
        int[] need = new int[4];
        int[] primes = {2, 3, 5, 7};

        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                need[i]++;
                t /= primes[i];
            }
        }

        // t still has another prime factor.
        if (t != 1)
            return "-1";

        A = need[0];
        B = need[1];
        C = need[2];
        D = need[3];

        buildDP();

        int n = num.length();

        /*
         * ------------------------------------------------------------
         * 1. Is num itself already a valid answer?
         * ------------------------------------------------------------
         */
        int[] remaining = need.clone();
        boolean zero = false;

        for (int i = 0; i < n; i++) {
            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                zero = true;
                break;
            }

            subtract(remaining, digit);
        }

        if (!zero && get(remaining) == 0)
            return num;

        /*
         * ------------------------------------------------------------
         * 2. Try to find the smallest answer with the SAME length.
         * ------------------------------------------------------------
         *
         * prefixNeed[i] = requirements remaining after processing
         * num[0 .. i-1].
         */
        int[][] prefixNeed = new int[n + 1][4];

        prefixNeed[0] = need.clone();

        int firstZero = -1;

        for (int i = 0; i < n; i++) {
            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                firstZero = i;
                break;
            }

            prefixNeed[i + 1] =
                copyAndSubtract(prefixNeed[i], digit);
        }

        /*
         * We want the RIGHTMOST position that can be increased.
         *
         * Why rightmost?
         *
         * Example:
         *
         * 123xxx
         * ^ ^
         *
         * Increasing a later digit gives a smaller number than
         * increasing an earlier digit.
         */
        int bestPos = -1;
        int bestDigit = -1;

        /*
         * If there is a zero at position z, we can only keep the
         * prefix before z. Therefore positions after z cannot be
         * reached while staying >= num with the same length.
         */
        int lastPosition = (firstZero == -1)
                ? n - 1
                : firstZero;

        for (int i = lastPosition; i >= 0; i--) {

            int currentDigit = num.charAt(i) - '0';

            /*
             * Try the smallest digit greater than num[i].
             */
            for (int digit = currentDigit + 1; digit <= 9; digit++) {

                int[] nextNeed =
                    copyAndSubtract(prefixNeed[i], digit);

                int suffixLength = n - i - 1;

                /*
                 * Can the remaining suffix satisfy the requirements?
                 */
                if (get(nextNeed) <= suffixLength) {
                    bestPos = i;
                    bestDigit = digit;
                    break;
                }
            }

            if (bestPos != -1)
                break;
        }

        if (bestPos != -1) {

            StringBuilder ans = new StringBuilder(n);

            // Same prefix.
            ans.append(num, 0, bestPos);

            // Increased digit.
            ans.append((char) ('0' + bestDigit));

            // Smallest possible suffix.
            int[] nextNeed =
                copyAndSubtract(prefixNeed[bestPos], bestDigit);

            ans.append(
                buildSmallest(
                    nextNeed,
                    n - bestPos - 1
                )
            );

            return ans.toString();
        }

        /*
         * ------------------------------------------------------------
         * 3. No answer with the same length.
         * ------------------------------------------------------------
         *
         * The smallest possible longer answer has:
         *
         *     max(n + 1, minimumRequiredDigits)
         *
         * digits.
         *
         * Since the first digit cannot be zero, buildSmallest()
         * naturally chooses 1 when possible.
         */
        int len = Math.max(n + 1, get(need));

        return buildSmallest(need.clone(), len);
    }
}
