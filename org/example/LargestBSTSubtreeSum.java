package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class LargestBSTSubtreeSum {

    // Definition for a binary tree node.
    class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }
    }

    // Helper class to store information about each subtree
    class SubtreeInfo {
        int minValue;        // Minimum value in the subtree
        int maxValue;        // Maximum value in the subtree
        int subtreeSum;     // Sum of all nodes in the subtree
        int largestBSTSum;  // Maximum sum of all BST subtrees encountered so far
        boolean isBST;      // Whether the subtree is a BST

        SubtreeInfo(int minValue, int maxValue, int subtreeSum, int largestBSTSum, boolean isBST) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.subtreeSum = subtreeSum;
            this.largestBSTSum = largestBSTSum;
            this.isBST = isBST;
        }
    }

    // Main function to find the sum of the largest BST subtree
    public int getLargestBSTSubtreeSum(TreeNode root) {
        return analyzeSubtree(root).largestBSTSum;
    }

    // Depth-first search function to compute SubtreeInfo for each subtree
    private SubtreeInfo analyzeSubtree(TreeNode node) {
        // Base case: if the node is null, return a default SubtreeInfo
        if (node == null) {
            return new SubtreeInfo(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0, true);
        }

        // Recurse on the left and right subtrees
        SubtreeInfo leftInfo = analyzeSubtree(node.left);
        SubtreeInfo rightInfo = analyzeSubtree(node.right);

        // Check if the current subtree is a BST
        if (leftInfo.isBST && rightInfo.isBST && leftInfo.maxValue < node.value && node.value < rightInfo.minValue) {
            // Current subtree is a BST
            int currentSubtreeSum = leftInfo.subtreeSum + rightInfo.subtreeSum + node.value;
            int currentMinValue = Math.min(node.value, leftInfo.minValue);
            int currentMaxValue = Math.max(node.value, rightInfo.maxValue);
            int maxBSTSubtreeSum = Math.max(currentSubtreeSum, Math.max(leftInfo.largestBSTSum, rightInfo.largestBSTSum));
            return new SubtreeInfo(currentMinValue, currentMaxValue, currentSubtreeSum, maxBSTSubtreeSum, true);
        } else {
            // Current subtree is not a BST
            int maxBSTSubtreeSum = Math.max(leftInfo.largestBSTSum, rightInfo.largestBSTSum);
            return new SubtreeInfo(0, 0, 0, maxBSTSubtreeSum, false);
        }
    }

    // Helper function to build a binary tree from an array
    public TreeNode buildBinaryTree(Integer[] values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int index = 1;

        while (!queue.isEmpty() && index < values.length) {
            TreeNode current = queue.poll();

            if (values[index] != null) {
                current.left = new TreeNode(values[index]);
                queue.add(current.left);
            }
            index++;

            if (index < values.length && values[index] != null) {
                current.right = new TreeNode(values[index]);
                queue.add(current.right);
            }
            index++;
        }

        return root;
    }

    public static void main(String[] args) {
        LargestBSTSubtreeSum solution = new LargestBSTSubtreeSum();

        // Input: Forest [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
        Integer[] nodes = {1, 4, 3, 2, 4, 2, 5, null, null, null, null, null, null, 4, 6};
        TreeNode root = solution.buildBinaryTree(nodes);

        // Find and print the largest BST subtree sum
        int largestBSTSum = solution.getLargestBSTSubtreeSum(root);
        System.out.println("The largest BST subtree sum is: " + largestBSTSum);
    }
}
