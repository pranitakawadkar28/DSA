class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long lo = 1;
        long hi = (long) coins[0] * k;

        // A safer upper bound is min(coin) * k.
        for (int coin : coins) {
            hi = Math.min(hi, (long) coin * k);
        }

        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;

            if (count(mid, coins) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long result = 0;

        // Inclusion-Exclusion over all subsets.
        for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    lcm = lcm(lcm, coins[i]);

                    // If LCM > x, this subset contributes 0.
                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            long contribution = x / lcm;

            if ((bits & 1) == 1) {
                result += contribution;
            } else {
                result -= contribution;
            }
        }

        return result;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}