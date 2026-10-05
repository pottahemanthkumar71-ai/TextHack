package graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Ford-Fulkerson maximum-flow algorithm using DFS to find augmenting paths.
 */
public class FordFulkerson {

    private FordFulkerson() {
        // Utility class.
    }

    public static int maxFlow(int[][] capacity, int source, int sink) {
        validate(capacity, source, sink);

        int n = capacity.length;
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) {
            residual[i] = Arrays.copyOf(capacity[i], n);
        }

        int maxFlow = 0;
        while (true) {
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            boolean[] visited = new boolean[n];
            int pathFlow = dfs(residual, source, sink, Integer.MAX_VALUE, visited, parent);

            if (pathFlow == 0) {
                break;
            }

            maxFlow += pathFlow;
            int current = sink;
            while (current != source) {
                int previous = parent[current];
                residual[previous][current] -= pathFlow;
                residual[current][previous] += pathFlow;
                current = previous;
            }
        }

        return maxFlow;
    }

    private static int dfs(int[][] residual, int current, int sink, int flow,
                           boolean[] visited, int[] parent) {
        if (current == sink) {
            return flow;
        }

        visited[current] = true;

        for (int next = 0; next < residual.length; next++) {
            if (!visited[next] && residual[current][next] > 0) {
                parent[next] = current;
                int newFlow = Math.min(flow, residual[current][next]);
                int result = dfs(residual, next, sink, newFlow, visited, parent);
                if (result > 0) {
                    return result;
                }
            }
        }

        return 0;
    }

    private static void validate(int[][] capacity, int source, int sink) {
        if (capacity == null || capacity.length == 0) {
            throw new IllegalArgumentException("Capacity matrix cannot be empty.");
        }
        int n = capacity.length;
        for (int[] row : capacity) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException("Capacity matrix must be square.");
            }
            for (int value : row) {
                if (value < 0) {
                    throw new IllegalArgumentException("Capacity cannot be negative.");
                }
            }
        }
        if (source < 0 || source >= n || sink < 0 || sink >= n || source == sink) {
            throw new IllegalArgumentException("Invalid source or sink.");
        }
    }
}
