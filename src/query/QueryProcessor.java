package query;

import dynamicprogramming.EditDistance;
import dynamicprogramming.SequenceAlignment;
import model.Article;
import repository.ArticleRepository;
import search.KMP;
import search.NaiveSearch;
import search.RabinKarp;
import search.ZFunction;
import search.FuzzySearch;
import similarity.SimilarityAnalyzer;
import similarity.SuffixProcessor;

import java.util.List;

public class QueryProcessor {
    private final ArticleRepository repository;

    public QueryProcessor(ArticleRepository repository) {
        this.repository = repository;
    }

    public void keywordSearch(String query) {
        System.out.println("\n========== KEYWORD SEARCH ==========");
        int displayed = 0;

        for (Article article : repository.getArticles()) {
            List<Integer> matches = KMP.search(article.getFullText(), query);

            if (!matches.isEmpty()) {
                System.out.println("\n[" + article.getId() + "] " + article.getTitle());
                System.out.println("Occurrences: " + matches.size());
                displayed++;
                if (displayed >= 10) break;
            }
        }

        if (displayed == 0) System.out.println("No exact keyword matches found.");
    }

    public void compareAlgorithms(String query) {
        System.out.println("\n========== PATTERN MATCHING COMPARISON ==========");

        if (repository.size() == 0) {
            System.out.println("Repository is empty.");
            return;
        }
        if (query == null || query.isEmpty()) {
            System.out.println("Pattern cannot be empty.");
            return;
        }

        Article article = repository.getArticle(0);
        String text = article.getFullText();

        long start = System.nanoTime();
        List<Integer> naive = NaiveSearch.search(text, query);
        long naiveTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<Integer> kmp = KMP.search(text, query);
        long kmpTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<Integer> rk = RabinKarp.search(text, query);
        long rkTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<Integer> z = ZFunction.search(text, query);
        long zTime = System.nanoTime() - start;

        System.out.println("Article: " + article.getTitle());
        System.out.println("Pattern: " + query);
        System.out.println();
        System.out.println("Naive occurrences : " + naive.size());
        System.out.println("KMP occurrences   : " + kmp.size());
        System.out.println("Rabin-Karp matches: " + rk.size());
        System.out.println("Z-function matches: " + z.size());
        System.out.println();
        System.out.println("Naive time       : " + naiveTime + " ns");
        System.out.println("KMP time         : " + kmpTime + " ns");
        System.out.println("Rabin-Karp time  : " + rkTime + " ns");
        System.out.println("Z-function time  : " + zTime + " ns");

        boolean consistent = naive.equals(kmp) && kmp.equals(rk) && rk.equals(z);
        System.out.println("All algorithms agree: " + consistent);
    }

    public void fuzzySearch(String query) {
        System.out.println("\n========== FUZZY SEARCH ==========");

        List<FuzzySearch.Match> matches =
                FuzzySearch.search(repository.getArticles(), query, 3);

        if (matches.isEmpty()) {
            System.out.println("No fuzzy matches found.");
            return;
        }

        int limit = Math.min(10, matches.size());
        for (int i = 0; i < limit; i++) {
            FuzzySearch.Match match = matches.get(i);
            System.out.println(match.article.getTitle() +
                    " | Edit Distance = " + match.distance);
        }
    }

    public void articleSimilarity(int first, int second) {
        Article a = repository.getArticle(first);
        Article b = repository.getArticle(second);

        if (a == null || b == null) {
            System.out.println("Invalid article numbers.");
            return;
        }

        double similarity = SimilarityAnalyzer.calculate(
                a.getFullText(), b.getFullText());

        System.out.println("\n========== SIMILARITY ==========");
        System.out.println("Article A: " + a.getTitle());
        System.out.println("Article B: " + b.getTitle());
        System.out.printf("Similarity: %.2f%%%n", similarity);
    }

    public void sequenceAlignment(int first, int second) {
        Article a = repository.getArticle(first);
        Article b = repository.getArticle(second);

        if (a == null || b == null) {
            System.out.println("Invalid article numbers.");
            return;
        }

        int score = SequenceAlignment.calculateScore(
                a.getTitle(), b.getTitle());

        System.out.println("\n========== SEQUENCE ALIGNMENT ==========");
        System.out.println("A: " + a.getTitle());
        System.out.println("B: " + b.getTitle());
        System.out.println("Alignment score: " + score);
    }

    public void editDistance(String a, String b) {
        int distance = EditDistance.calculate(a, b);

        System.out.println("\n========== EDIT DISTANCE ==========");
        System.out.println("String 1: " + a);
        System.out.println("String 2: " + b);
        System.out.println("Minimum operations: " + distance);
    }

    public void suffixDemo(String text) {
        System.out.println("\n========== SUFFIX PROCESSING ==========");

        String[] suffixes = SuffixProcessor.buildSuffixArray(text);
        int limit = Math.min(10, suffixes.length);

        for (int i = 0; i < limit; i++) {
            String suffix = suffixes[i];
            if (suffix.length() > 80)
                suffix = suffix.substring(0, 80) + "...";

            System.out.println((i + 1) + ". " + suffix);
        }
    }
}
