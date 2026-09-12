class Solution {
    static class Interval {
        int l, r, weight, index;

        Interval(int l, int r, int weight, int index) {
            this.l = l;
            this.r = r;
            this.weight = weight;
            this.index = index;
        }
    }

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        // Sort by left endpoint
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l)
                return Integer.compare(a.l, b.l);
            return Integer.compare(a.r, b.r);
        });

        // next[i] = first interval whose left > arr[i].r
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            int lo = i + 1;
            int hi = n;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;

                if (arr[mid].l > arr[i].r) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            next[i] = lo;
        }

        /*
         * dp[i][k] =
         * best answer from intervals i...n-1
         * using at most k intervals.
         */
        State[][] dp = new State[n + 1][5];

        // Base case
        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new State(0, new ArrayList<>());
        }

        for (int i = n - 1; i >= 0; i--) {

            // k = 0 => cannot select anything
            dp[i][0] = new State(0, new ArrayList<>());

            for (int k = 1; k <= 4; k++) {

                // Option 1: Skip current interval
                State skip = dp[i + 1][k];

                // Option 2: Take current interval
                State nextState = dp[next[i]][k - 1];

                List<Integer> takeIndices =
                    new ArrayList<>(nextState.indices);

                takeIndices.add(arr[i].index);

                // The answer must be sorted by original indices
                Collections.sort(takeIndices);

                State take = new State(
                    nextState.score + arr[i].weight,
                    takeIndices
                );

                // Choose the better option
                if (take.score > skip.score) {
                    dp[i][k] = take;
                } 
                else if (take.score < skip.score) {
                    dp[i][k] = skip;
                } 
                else {
                    // Same score -> lexicographically smaller indices
                    if (lexicographicallySmaller(
                            take.indices,
                            skip.indices)) {

                        dp[i][k] = take;
                    } else {
                        dp[i][k] = skip;
                    }
                }
            }
        }

        List<Integer> answer = dp[0][4].indices;

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    private boolean lexicographicallySmaller(
            List<Integer> a,
            List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}