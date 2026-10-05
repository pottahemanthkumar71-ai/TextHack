package search;

import java.util.ArrayList;
import java.util.List;

/**
 * Z-algorithm based pattern matching.
 *
 * The Z value at position i is the length of the longest substring
 * starting at i that matches the prefix of the same string.
 */
public class ZFunction {

    private ZFunction() {
        // Utility class.
    }

    public static int[] buildZ(String text) {
        int n = text.length();
        int[] z = new int[n];
        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while (i + z[i] < n && text.charAt(z[i]) == text.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();

        if (pattern == null || text == null) {
            return matches;
        }

        if (pattern.isEmpty()) {
            for (int i = 0; i <= text.length(); i++) {
                matches.add(i);
            }
            return matches;
        }

        // Use a separator that does not match ordinary input characters.
        char separator = chooseSeparator(text, pattern);
        String combined = pattern + separator + text;
        int[] z = buildZ(combined);

        int patternLength = pattern.length();
        for (int i = patternLength + 1; i < combined.length(); i++) {
            if (z[i] >= patternLength) {
                matches.add(i - patternLength - 1);
            }
        }

        return matches;
    }

    private static char chooseSeparator(String text, String pattern) {
        for (char separator = 1; separator < Character.MAX_VALUE; separator++) {
            if (text.indexOf(separator) == -1 && pattern.indexOf(separator) == -1) {
                return separator;
            }
        }
        // Extremely unlikely for normal Wikipedia text.
        return '\0';
    }
}
