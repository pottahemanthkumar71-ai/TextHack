package dynamicprogramming;

public class SequenceAlignment {
    private static final int MATCH = 2;
    private static final int MISMATCH = -1;
    private static final int GAP = -2;

    public static int calculateScore(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) dp[i][0] = dp[i - 1][0] + GAP;
        for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j - 1] + GAP;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int diagonal = dp[i - 1][j - 1] +
                        (a.charAt(i - 1) == b.charAt(j - 1) ? MATCH : MISMATCH);
                int up = dp[i - 1][j] + GAP;
                int left = dp[i][j - 1] + GAP;
                dp[i][j] = Math.max(diagonal, Math.max(up, left));
            }
        }
        return dp[m][n];
    }
}
