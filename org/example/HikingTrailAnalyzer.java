package org.example;

public class HikingTrailAnalyzer {

    public static int findMaxHikeLength(int[] elevations, int maxElevationChange) {
        int longestHike = 0; // Tracks the longest continuous hike length
        int currentHikeLength = 1; // Tracks the current length of a valid hike

        for (int i = 1; i < elevations.length; i++) {
            // Check if the current segment is an uphill hike within the allowed elevation change
            if (elevations[i] > elevations[i - 1] && elevations[i] - elevations[i - 1] <= maxElevationChange) {
                currentHikeLength++; // Extend the current hike length
            } else {
                // Reset the current hike length as the segment is no longer valid
                currentHikeLength = 1;
            }
            // Update the longest hike length found
            longestHike = Math.max(longestHike, currentHikeLength);
        }

        return longestHike;
    }

    public static void main(String[] args) {
        int[] trailElevations = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int maxElevationChange = 3;

        int maxHikeLength = findMaxHikeLength(trailElevations, maxElevationChange);
        System.out.println("The longest continuous hike length is: " + maxHikeLength);
    }
}
