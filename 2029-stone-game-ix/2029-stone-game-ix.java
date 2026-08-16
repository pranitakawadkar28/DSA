class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] count = new int[3];

        for (int stone : stones) {
            count[stone % 3]++;
        }

        // Even number of 0-mod-3 stones:
        // Alice needs at least one 1 and one 2.
        if (count[0] % 2 == 0) {
            return count[1] > 0 && count[2] > 0;
        }

        // Odd number of 0-mod-3 stones:
        // One remainder type must dominate by more than 2.
        return Math.abs(count[1] - count[2]) > 2;
    }
}