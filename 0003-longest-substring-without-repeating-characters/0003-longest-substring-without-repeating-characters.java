class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int maxAns = 0;

        for(int i=0; i<n; i++){
            HashSet<Character> set = new HashSet<>();
            for(int j=i; j<n; j++){

                char ch = s.charAt(j);

                if(set.contains(ch)){
                    break;
                }

                set.add(ch);

                maxAns = Math.max(maxAns, j-i+1);
            }
        }

        return maxAns;
    }
}