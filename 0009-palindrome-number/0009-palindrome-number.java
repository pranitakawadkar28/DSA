class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0) return false;

        int target = x;
        int rev = 0;

        while(x != 0){
            int temp = x % 10;
            x = x / 10;
            rev = rev * 10 + temp;
        }

        return target == rev;
    }
}