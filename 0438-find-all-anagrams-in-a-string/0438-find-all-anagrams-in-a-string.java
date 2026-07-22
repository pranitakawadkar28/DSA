class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        // Result list jisme anagram ke starting index store honge
        List<Integer> res = new ArrayList<>();

        int n = s.length();
        int k = p.length();

        // Agar pattern bada hai string se, to anagram possible hi nahi
        if (k > n) return res;

        // Pattern ke characters ki frequency store karenge
        int[] pCount = new int[26];

        // Pattern ki frequency count
        for (int i = 0; i < k; i++) {
            pCount[p.charAt(i) - 'a']++;
        }

        // String me har possible window of size k check karenge
        for (int i = 0; i <= n - k; i++) {

            // Current window ki frequency
            int[] sCount = new int[26];

            // Window ke har character ki frequency count karo
            for (int j = i; j < i + k; j++) {
                sCount[s.charAt(j) - 'a']++;
            }

            // Agar dono frequency arrays same hain
            // to current window ek anagram hai
            if (Arrays.equals(pCount, sCount)) {
                res.add(i);   // Starting index add karo
            }
        }

        return res;
    }
}