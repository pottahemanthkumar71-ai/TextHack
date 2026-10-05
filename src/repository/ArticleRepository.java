package repository;

import model.Article;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

    public void addArticle(Article article) { articles.add(article); }
    public List<Article> getArticles() { return articles; }
    public Article getArticle(int index) {
        return (index < 0 || index >= articles.size()) ? null : articles.get(index);
    }
    public int size() { return articles.size(); }
    public void clear() { articles.clear(); }
}
