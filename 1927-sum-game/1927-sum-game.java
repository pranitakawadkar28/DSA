class Solution {
    public boolean sumGame(String num) {
         int n = num.length();
        int half = n / 2;

        int diff = 0;
        int leftQ = 0;
        int rightQ = 0;

        // First half
        for (int i = 0; i < half; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                leftQ++;
            } else {
                diff += c - '0';
            }
        }

        // Second half
        for (int i = half; i < n; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                rightQ++;
            } else {
                diff -= c - '0';
            }
        }

        // Bob can force equality only in this exact case.
        return 2 * diff != 9 * (rightQ - leftQ);
    }
}