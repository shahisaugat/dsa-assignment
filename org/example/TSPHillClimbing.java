package org.example;

import java.util.Arrays;
import java.util.Random;

public class TSPHillClimbing {

    private static final Random RANDOM = new Random();

    // Method to create a random distance matrix
    public static double[][] createDistanceMatrix(int numCities) {
        double[][] distMatrix = new double[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j) {
                    distMatrix[i][j] = RANDOM.nextDouble() * 100;
                } else {
                    distMatrix[i][j] = 0; // Distance to self is zero
                }
            }
        }
        return distMatrix;
    }

    // Method to calculate the total distance of the tour
    public static double totalDistance(int[] tour, double[][] distMatrix) {
        double distance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            distance += distMatrix[tour[i]][tour[i + 1]];
        }
        distance += distMatrix[tour[tour.length - 1]][tour[0]]; // Return to start
        return distance;
    }

    // Method to generate a neighbor by swapping two cities
    public static void generateNeighbor(int[] tour) {
        int i = RANDOM.nextInt(tour.length);
        int j = RANDOM.nextInt(tour.length);
        while (j == i) {
            j = RANDOM.nextInt(tour.length);
        }
        // Swap cities
        int temp = tour[i];
        tour[i] = tour[j];
        tour[j] = temp;
    }

    // Hill-climbing algorithm
    public static int[] hillClimbing(double[][] distMatrix) {
        int numCities = distMatrix.length;
        int[] currentTour = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            currentTour[i] = i;
        }
        double currentDistance = totalDistance(currentTour, distMatrix);

        boolean improved = true;

        while (improved) {
            improved = false;
            int[] bestTour = currentTour.clone();
            double bestDistance = currentDistance;

            for (int i = 0; i < 100; i++) { // Number of neighbors to generate
                int[] neighborTour = currentTour.clone();
                generateNeighbor(neighborTour);
                double neighborDistance = totalDistance(neighborTour, distMatrix);

                if (neighborDistance < bestDistance) {
                    bestDistance = neighborDistance;
                    bestTour = neighborTour;
                    improved = true;
                }
            }

            if (improved) {
                currentTour = bestTour;
                currentDistance = bestDistance;
            }
        }

        return currentTour;
    }

    public static void main(String[] args) {
        int numCities = 5;
        double[][] distMatrix = createDistanceMatrix(numCities);

        int[] bestTour = hillClimbing(distMatrix);
        double bestDistance = totalDistance(bestTour, distMatrix);

        System.out.println("Best tour: " + Arrays.toString(bestTour));
        System.out.println("Total distance: " + bestDistance);
    }
}
