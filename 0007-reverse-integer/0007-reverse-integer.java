class Solution {
    public int reverse(int x) {
       int rev = 0;

        while (x != 0) {

            int digit = x % 10;
            x = x / 10;

            // Check overflow before updating rev
            if (rev > 0 && rev > (Integer.MAX_VALUE - digit) / 10 ||
                rev < 0 && rev < (Integer.MIN_VALUE - digit) / 10) {
                return 0;
            }

            rev = rev * 10 + digit;
        }

        return rev;
    }
}