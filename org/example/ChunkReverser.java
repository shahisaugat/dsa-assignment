package org.example;

import java.util.Arrays;

public class ChunkReverser {

    // Definition for singly-linked list.
    static class ListNode {
        int value;
        ListNode next;
        ListNode(int x) { value = x; }
    }

    public static void main(String[] args) {
        System.out.println("Example 1:");
        int[] inputArray1 = {1, 2, 3, 4, 5};
        int chunkSize1 = 2;
        ListNode head1 = arrayToList(inputArray1);
        ListNode reversedHead1 = reverseInChunks(head1, chunkSize1);
        int[] resultArray1 = listToArray(reversedHead1);
        System.out.println("Reversed Sequence: " + Arrays.toString(resultArray1));

        System.out.println("Example 2:");
        int[] inputArray2 = {1, 2, 3, 4, 5};
        int chunkSize2 = 3;
        ListNode head2 = arrayToList(inputArray2);
        ListNode reversedHead2 = reverseInChunks(head2, chunkSize2);
        int[] resultArray2 = listToArray(reversedHead2);
        System.out.println("Reversed Sequence: " + Arrays.toString(resultArray2));
    }

    // Function to reverse nodes in chunks of size k
    public static ListNode reverseInChunks(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode previousChunkEnd = dummy;
        ListNode currentNode = head;

        // Count the number of nodes in the linked list
        int nodeCount = 0;
        while (currentNode != null) {
            nodeCount++;
            currentNode = currentNode.next;
        }

        currentNode = dummy.next;
        while (nodeCount >= k) {
            ListNode chunkStart = currentNode;
            ListNode previousNode = null;
            ListNode tempNode = null;

            // Reverse k nodes
            for (int i = 0; i < k; i++) {
                tempNode = currentNode.next;
                currentNode.next = previousNode;
                previousNode = currentNode;
                currentNode = tempNode;
            }

            // Connect with the previous part
            previousChunkEnd.next = previousNode;
            chunkStart.next = currentNode;
            previousChunkEnd = chunkStart;

            nodeCount -= k;
        }

        return dummy.next;
    }

    // Helper method to convert an array to a linked list
    private static ListNode arrayToList(int[] array) {
        ListNode dummy = new ListNode(0);
        ListNode currentNode = dummy;
        for (int num : array) {
            currentNode.next = new ListNode(num);
            currentNode = currentNode.next;
        }
        return dummy.next;
    }

    // Helper method to convert a linked list to an array
    private static int[] listToArray(ListNode head) {
        int size = 0;
        ListNode tempNode = head;
        while (tempNode != null) {
            size++;
            tempNode = tempNode.next;
        }

        int[] array = new int[size];
        tempNode = head;
        int index = 0;
        while (tempNode != null) {
            array[index++] = tempNode.value;
            tempNode = tempNode.next;
        }
        return array;
    }
}
