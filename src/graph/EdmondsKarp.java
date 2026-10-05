package graph;

import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;

/**
 * Edmonds-Karp algorithm: Ford-Fulkerson with BFS for shortest augmenting paths.
 */
public class EdmondsKarp {

    private EdmondsKarp() {
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
            int[] parent = bfs(residual, source, sink);
            if (parent[sink] == -1) {
                break;
            }

            int pathFlow = Integer.MAX_VALUE;
            int current = sink;
            while (current != source) {
                int previous = parent[current];
                pathFlow = Math.min(pathFlow, residual[previous][current]);
                current = previous;
            }

            current = sink;
            while (current != source) {
                int previous = parent[current];
                residual[previous][current] -= pathFlow;
                residual[current][previous] += pathFlow;
                current = previous;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private static int[] bfs(int[][] residual, int source, int sink) {
        int n = residual.length;
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next = 0; next < n; next++) {
                if (!visited[next] && residual[current][next] > 0) {
                    parent[next] = current;
                    visited[next] = true;
                    if (next == sink) {
                        return parent;
                    }
                    queue.offer(next);
                }
            }
        }

        return parent;
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
