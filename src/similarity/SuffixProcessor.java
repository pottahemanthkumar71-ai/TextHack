package similarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuffixProcessor {
    public static String[] buildSuffixArray(String text) {
        text = text.toLowerCase();
        List<String> suffixes = new ArrayList<>();

        for (int i = 0; i < text.length(); i++)
            suffixes.add(text.substring(i));

        String[] result = suffixes.toArray(new String[0]);
        Arrays.sort(result);
        return result;
    }

    public static int longestCommonPrefix(String a, String b) {
        int length = Math.min(a.length(), b.length());
        int i = 0;

        while (i < length && a.charAt(i) == b.charAt(i)) i++;
        return i;
    }
}
