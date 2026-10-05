package search;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    private static final int PRIME = 101;
    private static final int BASE = 256;

    public static List<Integer> search(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        if (text == null || pattern == null) return positions;

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return positions;

        long patternHash = 0, textHash = 0, h = 1;

        for (int i = 0; i < m - 1; i++) h = (h * BASE) % PRIME;

        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % PRIME;
            textHash = (BASE * textHash + text.charAt(i)) % PRIME;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) positions.add(i);
            }

            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h)
                           + text.charAt(i + m)) % PRIME;
                if (textHash < 0) textHash += PRIME;
            }
        }
        return positions;
    }
}
