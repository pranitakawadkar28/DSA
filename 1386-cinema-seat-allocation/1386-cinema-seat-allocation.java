class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // Only seats 2 through 9 matter.
            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Every completely empty row can accommodate 2 groups.
        long answer = 2L * n;

        for (int mask : map.values()) {
            // This row was initially counted as 2 groups.
            // Determine how many groups it can actually accommodate.

            boolean left = (mask & ((1 << 2) | (1 << 3) | (1 << 4) | (1 << 5))) == 0;

            boolean middle = (mask & ((1 << 4) | (1 << 5) | (1 << 6) | (1 << 7))) == 0;

            boolean right = (mask & ((1 << 6) | (1 << 7) | (1 << 8) | (1 << 9))) == 0;

            // If both left and right are available, we can fit 2 groups.
            if (left && right) {
                continue; // Already counted 2 for this row.
            }

            // Otherwise, this row can fit at most 1 group.
            if (left || middle || right) {
                answer--;
            } else {
                // It cannot fit any group.
                answer -= 2;
            }
        }

        return (int) answer;
    }
}