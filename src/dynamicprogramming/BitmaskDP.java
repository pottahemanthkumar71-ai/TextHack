package dynamicprogramming;

import java.util.Arrays;

public class BitmaskDP {
    public static int tsp(int[][] distance) {
        int n = distance.length;
        int totalMasks = 1 << n;
        int INF = Integer.MAX_VALUE / 4;
        int[][] dp = new int[totalMasks][n];

        for (int[] row : dp) Arrays.fill(row, INF);
        dp[1][0] = 0;

        for (int mask = 1; mask < totalMasks; mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0 || dp[mask][u] == INF) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;
                    int nextMask = mask | (1 << v);
                    dp[nextMask][v] = Math.min(
                        dp[nextMask][v], dp[mask][u] + distance[u][v]);
                }
            }
        }

        int answer = INF;
        int fullMask = totalMasks - 1;
        for (int i = 0; i < n; i++)
            answer = Math.min(answer, dp[fullMask][i] + distance[i][0]);

        return answer;
    }
}
