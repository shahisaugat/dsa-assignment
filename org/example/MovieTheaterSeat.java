package org.example;

import java.util.HashMap;

public class MovieTheaterSeat {

    public boolean canFriendsSitTogether(int[] seatNumbers, int maxIndexDiff, int maxSeatDiff) {
        if (seatNumbers == null || seatNumbers.length < 2) {
            return false;
        }

        HashMap<Integer, Integer> seatMap = new HashMap<>();

        for (int i = 0; i < seatNumbers.length; i++) {
            int currentSeat = seatNumbers[i];

            // Check for a valid seat in the range [currentSeat - maxSeatDiff, currentSeat + maxSeatDiff]
            for (int seat : seatMap.keySet()) {
                if (Math.abs(seat - currentSeat) <= maxSeatDiff) {
                    return true; // Friends can sit together based on the given conditions
                }
            }

            // Add the current seat number to the map
            seatMap.put(currentSeat, i);

            // Maintain the sliding window by removing seats that exceed the index difference limit
            if (i >= maxIndexDiff) {
                int seatToRemove = seatNumbers[i - maxIndexDiff];
                seatMap.remove(seatToRemove);
            }
        }

        return false;
    }

    public static void main(String[] args) {
        MovieTheaterSeat movieTheater = new MovieTheaterSeat();

        int[] seatNumbers = {1, 2, 4, 6, 7};
        int maxIndexDiff = 2;
        int maxSeatDiff = 1;

        System.out.println(movieTheater.canFriendsSitTogether(seatNumbers, maxIndexDiff, maxSeatDiff)); // Output: true
    }
}
