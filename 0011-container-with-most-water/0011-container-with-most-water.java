class Solution {
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int maxArea = 0;

        while(left < right){
            int h = Math.min(height[left], height[right]);
            int w = right - left;
            int a = h * w;

            maxArea = Math.max(maxArea, a);

            if(height[left] <= height[right]){
                left++;
            }
            else{
                right--;
            }
        }

        return maxArea;
    }
}