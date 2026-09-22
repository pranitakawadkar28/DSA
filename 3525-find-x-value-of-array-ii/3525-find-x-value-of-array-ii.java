class Solution {

    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    int n;
    int k;
    int[] nums;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.nums = nums;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Update persists for future queries
            nums[index] = value;
            update(1, 0, n - 1, index, value);

            // Get prefixes of nums[start ... n-1]
            Node res = query(1, 0, n - 1, start, n - 1);

            answer[i] = res.cnt[x];
        }

        return answer;
    }

    // Build segment tree
    private void build(int node, int l, int r) {

        if (l == r) {
            tree[node] = new Node(k);

            int value = nums[l] % k;

            tree[node].prod = value;
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Merge two segments
    private Node merge(Node left, Node right) {

        Node res = new Node(k);

        // Product of entire segment
        res.prod = (int) ((long) left.prod * right.prod % k);

        // Prefixes entirely inside left
        for (int r = 0; r < k; r++) {
            res.cnt[r] += left.cnt[r];
        }

        // Prefixes that extend from left into right
        for (int r = 0; r < k; r++) {

            if (right.cnt[r] == 0) {
                continue;
            }

            int newRemainder =
                    (int) ((long) left.prod * r % k);

            res.cnt[newRemainder] += right.cnt[r];
        }

        return res;
    }

    // Point update
    private void update(int node, int l, int r, int index, int value) {

        if (l == r) {

            tree[node] = new Node(k);

            int v = value % k;

            tree[node].prod = v;
            tree[node].cnt[v] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(
                tree[node * 2],
                tree[node * 2 + 1]
        );
    }

    // Range query
    private Node query(int node, int l, int r, int ql, int qr) {

        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = l + (r - l) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }
}