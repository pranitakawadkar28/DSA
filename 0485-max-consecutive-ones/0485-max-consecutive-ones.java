class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int mcnt = 0;
        int ccnt = 0;

        for(int i=0; i<nums.length; i++){
            if(nums[i] == 1){
                ccnt++;
            }
            else{
                if(ccnt > mcnt){
                    mcnt = ccnt;
                }

                ccnt = 0;
            }
        }

        return Math.max(mcnt, ccnt);
    }
}