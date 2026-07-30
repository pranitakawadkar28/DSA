class Solution {
    public boolean isPalindrome(int x) {
        
        if(x < 0) {
            return false;
        }

        int sum = 0;
        int target = x;
 
        while(x > 0){
            int temp = x % 10;
            x = x / 10;
            sum = sum * 10 + temp;
        }

        return sum == target;
    }
}