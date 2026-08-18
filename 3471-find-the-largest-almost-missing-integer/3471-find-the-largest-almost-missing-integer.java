class Solution {
    public int largestInteger(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();

        // There are n - k + 1 subarrays of size k
        for (int i = 0; i <= nums.length - k; i++) {
            Set<Integer> seen = new HashSet<>();

            // Process the current subarray
            for (int j = i; j < i + k; j++) {
                seen.add(nums[j]);
            }

            // Count this subarray once for each distinct number in it
            for (int x : seen) {
                count.put(x, count.getOrDefault(x, 0) + 1);
            }
        }

        int answer = -1;

        // Find the largest number appearing in exactly one subarray
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() == 1) {
                answer = Math.max(answer, entry.getKey());
            }
        }

        return answer;
    }
}