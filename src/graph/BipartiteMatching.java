package graph;

import java.util.Arrays;

/**
 * Maximum bipartite matching using the standard augmenting-path method.
 * This is equivalent to solving a unit-capacity bipartite flow network.
 */
public class BipartiteMatching {

    private BipartiteMatching() {
        // Utility class.
    }

    /**
     * graph[left][right] = true means this pair is allowed.
     * Returns the maximum number of matches.
     */
    public static int maximumMatching(boolean[][] graph) {
        validate(graph);

        int leftCount = graph.length;
        int rightCount = graph.length == 0 ? 0 : graph[0].length;
        int[] matchedRight = new int[rightCount];
        Arrays.fill(matchedRight, -1);

        int matches = 0;
        for (int left = 0; left < leftCount; left++) {
            boolean[] visitedRight = new boolean[rightCount];
            if (tryMatch(left, graph, visitedRight, matchedRight)) {
                matches++;
            }
        }
        return matches;
    }

    /**
     * Returns the matched right index for each left index.
     * An unmatched left vertex receives -1.
     */
    public static int[] getMatching(boolean[][] graph) {
        validate(graph);

        int leftCount = graph.length;
        int rightCount = graph.length == 0 ? 0 : graph[0].length;
        int[] matchedRight = new int[rightCount];
        int[] leftToRight = new int[leftCount];
        Arrays.fill(matchedRight, -1);
        Arrays.fill(leftToRight, -1);

        for (int left = 0; left < leftCount; left++) {
            boolean[] visitedRight = new boolean[rightCount];
            tryMatch(left, graph, visitedRight, matchedRight);
        }

        // Rebuild the final left -> right mapping after all augmenting paths.
        for (int right = 0; right < rightCount; right++) {
            int left = matchedRight[right];
            if (left != -1) {
                leftToRight[left] = right;
            }
        }
        return leftToRight;
    }

    private static boolean tryMatch(int left, boolean[][] graph,
                                    boolean[] visitedRight, int[] matchedRight) {
        for (int right = 0; right < graph[left].length; right++) {
            if (!graph[left][right] || visitedRight[right]) {
                continue;
            }

            visitedRight[right] = true;
            if (matchedRight[right] == -1 ||
                    tryMatch(matchedRight[right], graph, visitedRight, matchedRight)) {
                matchedRight[right] = left;
                return true;
            }
        }
        return false;
    }

    private static void validate(boolean[][] graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Bipartite graph cannot be null.");
        }
        if (graph.length == 0) {
            return;
        }
        if (graph[0] == null) {
            throw new IllegalArgumentException("Bipartite graph rows cannot be null.");
        }
        int rightCount = graph[0].length;
        for (boolean[] row : graph) {
            if (row == null || row.length != rightCount) {
                throw new IllegalArgumentException("All bipartite graph rows must have the same length.");
            }
        }
    }
}
