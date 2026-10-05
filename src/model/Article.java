package model;

public class Article {
    private int id;
    private String title;
    private String content;
    private String filePath;

    public Article(int id, String title, String content, String filePath) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getFilePath() { return filePath; }
    public String getFullText() { return title + " " + content; }

    @Override
    public String toString() {
        return id + ". " + title;
    }
}
