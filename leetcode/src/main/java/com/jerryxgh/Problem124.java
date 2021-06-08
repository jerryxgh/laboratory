package com.jerryxgh;

/**
 * https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
 */
class Problem124 {
    private int ans = Integer.MIN_VALUE;

    public int maxOneSidePath(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int right = Math.max(maxOneSidePath(root.right), 0);
        int left = Math.max(maxOneSidePath(root.left), 0);

        ans = Math.max(ans, right + left + root.val);

        return Math.max(right, left) + root.val;
    }

    public int maxPathSum(TreeNode root) {
        // 第二种答案
//        maxOneSidePath(root);
//        return ans;

        if (null == root) {
            return 0;
        }
        if (null == root.left && null == root.right) {
            return root.val;
        }

        int leftLeafPath = maxLeafPath(root.left);
        int rightLeafPath = maxLeafPath(root.right);
        int value = (leftLeafPath > 0 ? leftLeafPath : 0) + (rightLeafPath > 0 ? rightLeafPath : 0) + root.val;

        if (null != root.left) {
            value = Math.max(value, maxPathSum(root.left));
        }

        if (null != root.right) {
            value = Math.max(value, maxPathSum(root.right));
        }

        return value;
    }

    public int maxLeafPath(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int value = Math.max(maxLeafPath(root.left), maxLeafPath(root.right));
        if (value < 0) {
            return root.val;
        }
        return root.val + value;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}