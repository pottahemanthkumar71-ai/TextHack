package repository;

import model.Article;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CorpusLoader {
    private final Path rootDirectory;

    public CorpusLoader(String directory) {
        this.rootDirectory = Paths.get(directory);
    }

    public void loadCorpus(ArticleRepository repository) throws IOException {
        if (!Files.exists(rootDirectory))
            throw new IOException("Dataset directory not found: " + rootDirectory);
        if (!Files.isDirectory(rootDirectory))
            throw new IOException("Path is not a directory: " + rootDirectory);

        System.out.println("\nLoading Wikipedia corpus...");
        System.out.println("Root: " + rootDirectory);

        int[] articleId = {1};
        int[] fileCount = {0};

        try (var paths = Files.walk(rootDirectory)) {
            paths.filter(Files::isRegularFile)
                 .filter(this::isTextCorpusFile)
                 .forEach(path -> {
                     try {
                         fileCount[0]++;
                         System.out.println("Reading: " + rootDirectory.relativize(path));
                         loadFile(path, repository, articleId);
                     } catch (IOException e) {
                         System.out.println("Could not read: " + path);
                     }
                 });
        }

        System.out.println("\n================================");
        System.out.println("CORPUS LOADING COMPLETE");
        System.out.println("================================");
        System.out.println("Files loaded   : " + fileCount[0]);
        System.out.println("Articles loaded: " + repository.size());
    }

    private boolean isTextCorpusFile(Path path) {
        String name = path.getFileName().toString().toLowerCase();
        return name.startsWith("wiki_") || name.endsWith(".txt");
    }

    private void loadFile(Path path, ArticleRepository repository, int[] articleId)
            throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
        }

        parseArticles(lines, path, repository, articleId);
    }

    private void parseArticles(List<String> lines, Path source,
                               ArticleRepository repository, int[] articleId) {
        String currentTitle = null;
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            boolean possibleTitle =
                    line.length() <= 100 &&
                    !line.endsWith(".") && !line.endsWith(",") &&
                    !line.endsWith(";") && !line.endsWith(":") &&
                    !line.endsWith("!") && !line.endsWith("?");

            String nextLine = (i + 1 < lines.size()) ? lines.get(i + 1).trim() : "";

            if (currentTitle == null ||
                (possibleTitle && !nextLine.isEmpty() && content.length() > 100)) {

                if (currentTitle != null && content.length() > 0) {
                    repository.addArticle(new Article(
                            articleId[0]++, currentTitle, content.toString(), source.toString()));
                }

                currentTitle = line;
                content.setLength(0);
            } else {
                if (content.length() > 0) content.append(' ');
                content.append(line);
            }
        }

        if (currentTitle != null && content.length() > 0) {
            repository.addArticle(new Article(
                    articleId[0]++, currentTitle, content.toString(), source.toString()));
        }
    }
}
