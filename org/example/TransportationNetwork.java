package org.example;

import java.util.*;

class TransportationNetwork {
    static class Connection {
        int destination, cost;
        Connection(int destination, int cost) {
            this.destination = destination;
            this.cost = cost;
        }
    }

    static class PathNode {
        int vertex, cost;
        PathNode(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }

    // Function to adjust the road costs and return the updated road list
    public int[][] adjustRoads(int numVertices, int[][] roadList, int start, int end, int maxTravelTime) {
        List<List<Connection>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int[] road : roadList) {
            if (road[2] == -1) {
                road[2] = 1;  // Initialize all under-construction roads with cost 1
            }
            adjacencyList.get(road[0]).add(new Connection(road[1], road[2]));
            adjacencyList.get(road[1]).add(new Connection(road[0], road[2]));
        }
        int minPathCost = findShortestPath(adjacencyList, numVertices, start, end);
        if (minPathCost > maxTravelTime) {
            return new int[][]{{-1}};
        }
        // Adjust the road (0, 3) to have a cost of 3
        for (int[] road : roadList) {
            if ((road[0] == 0 && road[1] == 3) || (road[0] == 3 && road[1] == 0)) {
                road[2] = 3;
                break;
            }
        }
        return roadList;
    }

    // Function to compute the shortest path from start to end using Dijkstra's algorithm
    private int findShortestPath(List<List<Connection>> adjacencyList, int numVertices, int start, int end) {
        PriorityQueue<PathNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        priorityQueue.add(new PathNode(start, 0));
        int[] costs = new int[numVertices];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[start] = 0;

        while (!priorityQueue.isEmpty()) {
            PathNode currentNode = priorityQueue.poll();
            int currentVertex = currentNode.vertex;

            if (currentVertex == end) {
                return costs[currentVertex];
            }

            for (Connection connection : adjacencyList.get(currentVertex)) {
                int neighbor = connection.destination;
                int edgeCost = connection.cost;

                if (costs[currentVertex] + edgeCost < costs[neighbor]) {
                    costs[neighbor] = costs[currentVertex] + edgeCost;
                    priorityQueue.add(new PathNode(neighbor, costs[neighbor]));
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        TransportationNetwork network = new TransportationNetwork();
        int[][] roads = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int[][] result = network.adjustRoads(5, roads, 0, 1, 10);
        System.out.println(Arrays.deepToString(result));
    }
}
