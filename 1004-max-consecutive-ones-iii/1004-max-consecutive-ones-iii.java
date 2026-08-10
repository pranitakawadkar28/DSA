class Solution {
    public int longestOnes(int[] nums, int k) {
        int n = nums.length;
        int maxLength = 0;

        for(int i=0; i<n; i++){
            int zeroCnt = 0;
            for(int j=i; j<n; j++){
                if(nums[j] == 0){
                    zeroCnt++;
                }
                if(zeroCnt > k){
                    break;
                }

                maxLength = Math.max(maxLength, j - i + 1);
            }
        }
        return maxLength;
    }
}