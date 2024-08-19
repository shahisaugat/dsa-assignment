package org.example;

import java.util.*;

public class ClassroomTimeScheduler {
    static class ScheduledClass {
        int startTime;
        int endTime;
        int size;

        public ScheduledClass(int startTime, int endTime, int size) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.size = size;
        }
    }

    // Function to determine the most frequently used classroom
    public static int findMostUsedClassroom(int totalRooms, int[][] classDetails) {
        List<ScheduledClass> classes = new ArrayList<>();
        for (int[] details : classDetails) {
            classes.add(new ScheduledClass(details[0], details[1], details[2]));
        }

        // Sort classes by start time, then by class size (larger classes get priority)
        classes.sort((a, b) -> {
            if (a.startTime != b.startTime) return Integer.compare(a.startTime, b.startTime);
            return Integer.compare(b.size, a.size);
        });

        // Priority queue to track the end times of rooms
        PriorityQueue<int[]> roomQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] roomUsage = new int[totalRooms]; // Tracks the number of classes held in each room

        for (ScheduledClass cls : classes) {
            // Free up rooms that are available before the current class starts
            while (!roomQueue.isEmpty() && roomQueue.peek()[0] <= cls.startTime) {
                roomQueue.poll();
            }

            if (roomQueue.size() < totalRooms) {
                // Use an available room
                int room = roomQueue.size();
                roomQueue.add(new int[]{cls.endTime, room});
                roomUsage[room]++;
            } else {
                // Delay the class until the earliest room is free
                int[] earliestRoom = roomQueue.poll();
                int adjustedEndTime = earliestRoom[0] + (cls.endTime - cls.startTime);
                roomQueue.add(new int[]{adjustedEndTime, earliestRoom[1]});
                roomUsage[earliestRoom[1]]++;
            }
        }

        // Determine the room with the highest usage
        int maxUsage = 0;
        int mostUsedRoom = 0;
        for (int i = 0; i < totalRooms; i++) {
            if (roomUsage[i] > maxUsage) {
                maxUsage = roomUsage[i];
                mostUsedRoom = i;
            }
        }

        return mostUsedRoom;
    }

    public static void main(String[] args) {
        int totalRooms1 = 2;
        int[][] classDetails1 = {{0, 10, 30}, {1, 5, 20}, {2, 7, 25}, {3, 4, 15}};
        System.out.println(findMostUsedClassroom(totalRooms1, classDetails1)); // Output: 0

        int totalRooms2 = 3;
        int[][] classDetails2 = {{1, 20, 30}, {2, 10, 25}, {3, 5, 15}, {4, 9, 10}, {6, 8, 20}};
        System.out.println(findMostUsedClassroom(totalRooms2, classDetails2)); // Output: 1
    }
}
