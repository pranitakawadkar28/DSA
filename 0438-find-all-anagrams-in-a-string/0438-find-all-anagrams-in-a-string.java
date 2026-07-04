class Solution {
    public List<Integer> findAnagrams(String s, String p) {
         List<Integer> ans = new ArrayList<>();

        if (s.length() < p.length()) return ans;

        int[] freq = new int[26];

        // Store frequency of characters in p
        for (char c : p.toCharArray()) {
            freq[c - 'a']++;
        }

        int left = 0, right = 0;
        int count = p.length();

        while (right < s.length()) {
            // Include current character
            if (freq[s.charAt(right) - 'a'] > 0) {
                count--;
            }
            freq[s.charAt(right) - 'a']--;
            right++;

            // Found an anagram
            if (count == 0) {
                ans.add(left);
            }

            // Shrink window if its size equals p.length()
            if (right - left == p.length()) {
                if (freq[s.charAt(left) - 'a'] >= 0) {
                    count++;
                }
                freq[s.charAt(left) - 'a']++;
                left++;
            }
        }

        return ans;
    }
}