package similarity;

import java.util.HashSet;
import java.util.Set;

public class SimilarityAnalyzer {
    public static double calculate(String text1, String text2) {
        Set<String> words1 = tokenize(text1);
        Set<String> words2 = tokenize(text2);

        if (words1.isEmpty() || words2.isEmpty()) return 0.0;

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        return (double) intersection.size() / union.size() * 100.0;
    }

    private static Set<String> tokenize(String text) {
        Set<String> words = new HashSet<>();
        String[] tokens = text.toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .split("\s+");

        for (String token : tokens)
            if (!token.isBlank()) words.add(token);

        return words;
    }
}
