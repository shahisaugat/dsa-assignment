package org.example;

public class MessageDecoder {

    public static String decode(String message, int[][] operations) {
        char[] characters = message.toCharArray();
        // Apply each operation to the characters within the specified range
        for (int[] operation : operations) {
            int startIndex = operation[0];
            int endIndex = operation[1];
            int shiftDirection = operation[2];

            for (int i = startIndex; i <= endIndex; i++) {
                characters[i] = shiftCharacter(characters[i], shiftDirection);
            }
        }

        return new String(characters);
    }

    // Shifts the character based on the given direction
    private static char shiftCharacter(char c, int direction) {
        if (direction == 1) { // Shift forward
            return c == 'z' ? 'a' : (char) (c + 1);
        } else { // Shift backward
            return c == 'a' ? 'z' : (char) (c - 1);
        }
    }

    public static void main(String[] args) {
        String message = "hello";
        int[][] operations = { {0, 1, 1}, {2, 3, 0}, {0, 2, 1} };
        System.out.println(decode(message, operations));
        // Output: jglko
    }
}
