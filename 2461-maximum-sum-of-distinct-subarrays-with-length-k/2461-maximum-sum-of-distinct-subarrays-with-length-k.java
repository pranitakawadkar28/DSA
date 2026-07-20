class Solution {
    public long maximumSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        long windowSum = 0;
        long ans = 0;

        // First window
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        if (map.size() == k) {
            ans = windowSum;
        }

        // Slide the window
        for (int i = k; i < nums.length; i++) {

            // Remove left element
            int left = nums[i - k];
            map.put(left, map.get(left) - 1);

            if (map.get(left) == 0) {
                map.remove(left);
            }

            windowSum -= left;

            // Add right element
            int right = nums[i];
            map.put(right, map.getOrDefault(right, 0) + 1);
            windowSum += right;

            // Check if all elements are distinct
            if (map.size() == k) {
                ans = Math.max(ans, windowSum);
            }
        }

        return ans;
    }
}