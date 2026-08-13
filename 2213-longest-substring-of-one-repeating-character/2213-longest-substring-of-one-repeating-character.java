class Solution {
    class Node {
        int len;
        char leftChar, rightChar;
        int leftLen, rightLen, best;

        Node() {
            len = 0;
        }

        Node(char c) {
            len = 1;
            leftChar = rightChar = c;
            leftLen = rightLen = best = 1;
        }
    }

    Node[] tree;

    private Node merge(Node a, Node b) {
        if (a.len == 0) return b;
        if (b.len == 0) return a;

        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.leftLen = a.leftLen;
        res.rightLen = b.rightLen;

        // Best answer completely inside either half
        res.best = Math.max(a.best, b.best);

        // A repeating substring can cross the boundary
        if (a.rightChar == b.leftChar) {
            res.best = Math.max(
                res.best,
                a.rightLen + b.leftLen
            );

            // Entire left segment has one character
            if (a.leftLen == a.len) {
                res.leftLen = a.len + b.leftLen;
            }

            // Entire right segment has one character
            if (b.rightLen == b.len) {
                res.rightLen = b.len + a.rightLen;
            }
        }

        return res;
    }

    private void build(String s, int node, int l, int r) {
        if (l == r) {
            tree[node] = new Node(s.charAt(l));
            return;
        }

        int mid = l + (r - l) / 2;

        build(s, node * 2, l, mid);
        build(s, node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
        int node,
        
        int l,
        int r,
        int pos,
        char c
    ) {
        if (l == r) {
            tree[node] = new Node(c);
            return;
        }

        int mid = l + (r - l) / 2;

        if (pos <= mid) {
            update(node * 2, l, mid, pos, c);
        } else {
            update(node * 2 + 1, mid + 1, r, pos, c);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    } 
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryCharacters.length();

        tree = new Node[4 * n];

        build(s, 1, 0, n - 1);

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int pos = queryIndices[i];
            char c = queryCharacters.charAt(i);

            update(1, 0, n - 1, pos, c);

            // Root represents the entire string
            ans[i] = tree[1].best;
        }

        return ans;
    }
}