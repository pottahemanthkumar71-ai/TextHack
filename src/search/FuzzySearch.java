package search;

import dynamicprogramming.EditDistance;
import model.Article;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FuzzySearch {
    public static class Match {
        public Article article;
        public int distance;

        public Match(Article article, int distance) {
            this.article = article;
            this.distance = distance;
        }
    }

    public static List<Match> search(List<Article> articles, String query, int maxDistance) {
        List<Match> results = new ArrayList<>();

        for (Article article : articles) {
            int distance = EditDistance.calculate(query, article.getTitle());
            if (distance <= maxDistance)
                results.add(new Match(article, distance));
        }

        results.sort(Comparator.comparingInt(m -> m.distance));
        return results;
    }
}
