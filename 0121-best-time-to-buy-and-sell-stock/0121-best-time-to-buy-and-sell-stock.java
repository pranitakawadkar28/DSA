class Solution {
    public int maxProfit(int[] prices) {
        int minProfit = prices[0];
        int maxProfit = 0;

        for(int i=1; i<prices.length; i++){
            if(prices[i] < minProfit){
                minProfit = prices[i];
            }

            int profit = prices[i] - minProfit;

            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }
        return maxProfit;
    }
}