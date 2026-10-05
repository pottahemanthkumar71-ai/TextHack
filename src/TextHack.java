import model.Article;
import query.QueryProcessor;
import repository.ArticleRepository;
import repository.CorpusLoader;
import dynamicprogramming.BitmaskDP;
import graph.BipartiteMatching;
import graph.EdmondsKarp;
import graph.FordFulkerson;

import java.util.Scanner;

public class TextHack {
    private static final String DATASET = "D:\\Wikipedia";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArticleRepository repository = new ArticleRepository();

        System.out.println("==========================================");
        System.out.println("             TEXT HACK");
        System.out.println("   Wikipedia Text Search & Analysis");
        System.out.println("==========================================");
        System.out.println("\nDataset: " + DATASET);

        try {
            CorpusLoader loader = new CorpusLoader(DATASET);
            loader.loadCorpus(repository);
        } catch (Exception e) {
            System.out.println("\nERROR while loading corpus:");
            System.out.println(e.getMessage());
            scanner.close();
            return;
        }

        QueryProcessor processor = new QueryProcessor(repository);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("\nEnter choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> showRepository(repository);

                    case 2 -> {
                        System.out.print("Enter pattern: ");
                        processor.compareAlgorithms(scanner.nextLine());
                    }

                    case 3 -> {
                        System.out.print("Enter keyword: ");
                        processor.keywordSearch(scanner.nextLine());
                    }

                    case 4 -> {
                        System.out.print("Enter misspelled/partial title: ");
                        processor.fuzzySearch(scanner.nextLine());
                    }

                    case 5 -> {
                        System.out.print("Enter first article index: ");
                        int first = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter second article index: ");
                        int second = Integer.parseInt(scanner.nextLine());
                        processor.articleSimilarity(first - 1, second - 1);
                    }

                    case 6 -> {
                        System.out.print("Enter first article index: ");
                        int first = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter second article index: ");
                        int second = Integer.parseInt(scanner.nextLine());
                        processor.sequenceAlignment(first - 1, second - 1);
                    }

                    case 7 -> {
                        System.out.print("Enter string 1: ");
                        String a = scanner.nextLine();
                        System.out.print("Enter string 2: ");
                        String b = scanner.nextLine();
                        processor.editDistance(a, b);
                    }

                    case 8 -> runBitmaskDemo();

                    case 9 -> {
                        System.out.print("Enter text for suffix processing: ");
                        processor.suffixDemo(scanner.nextLine());
                    }

                    case 10 -> runFordFulkersonDemo(scanner);

                    case 11 -> runEdmondsKarpDemo(scanner);

                    case 12 -> runBipartiteMatchingDemo(scanner);

                    case 0 -> {
                        running = false;
                        System.out.println("\nThank you for using TextHack!");
                    }

                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numeric values.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n\n==========================================");
        System.out.println("              TEXT HACK MENU");
        System.out.println("==========================================");
        System.out.println("1. View Article Repository");
        System.out.println("2. Pattern Matching - Naive / KMP / Rabin-Karp / Z");
        System.out.println("3. Keyword Search - KMP");
        System.out.println("4. Fuzzy Search - Edit Distance");
        System.out.println("5. Document Similarity");
        System.out.println("6. Sequence Alignment");
        System.out.println("7. Edit Distance");
        System.out.println("8. Bitmask DP");
        System.out.println("9. Suffix Processing");
        System.out.println("10. Flow Network - Ford-Fulkerson");
        System.out.println("11. Flow Network - Edmonds-Karp");
        System.out.println("12. Bipartite Matching");
        System.out.println("0. Exit");
        System.out.println("==========================================");
    }

    private static void showRepository(ArticleRepository repository) {
        System.out.println("\n========== ARTICLE REPOSITORY ==========");
        System.out.println("Total articles: " + repository.size());

        int limit = Math.min(30, repository.size());

        for (int i = 0; i < limit; i++) {
            Article article = repository.getArticle(i);
            System.out.println((i + 1) + ". " + article.getTitle());
        }

        if (repository.size() > limit)
            System.out.println("... " + (repository.size() - limit) + " more articles");
    }


    private static void runFordFulkersonDemo(Scanner scanner) {
        System.out.println("\n========== FORD-FULKERSON: CITATION FLOW ==========");

        System.out.print("\nEnter number of vertices: ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        if (n <= 0) {
            System.out.println("Number of vertices must be greater than 0.");
            return;
        }

        int[][] capacity = new int[n][n];

        System.out.print("\nEnter number of directed citation edges: ");
        int edges = Integer.parseInt(scanner.nextLine().trim());

        if (edges < 0) {
            System.out.println("Number of edges cannot be negative.");
            return;
        }

        for (int i = 1; i <= edges; i++) {
            while (true) {
                System.out.println("\nEdge " + i);

                System.out.print("Enter source vertex: ");
                int from = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter destination vertex: ");
                int to = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter capacity: ");
                int cap = Integer.parseInt(scanner.nextLine().trim());

                if (from < 1 || from > n || to < 1 || to > n) {
                    System.out.println("Invalid vertex. Enter values from 1 to " + n + ".");
                    continue;
                }

                if (cap <= 0) {
                    System.out.println("Capacity must be greater than 0.");
                    continue;
                }

                // User enters vertices as 1, 2, 3, ...; Java arrays use 0, 1, 2, ...
                capacity[from - 1][to - 1] = cap;
                break;
            }
        }

        System.out.print("\nEnter source vertex: ");
        int source = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter destination vertex: ");
        int sink = Integer.parseInt(scanner.nextLine().trim());

        if (source < 1 || source > n || sink < 1 || sink > n) {
            System.out.println("Invalid source or destination vertex.");
            return;
        }

        if (source == sink) {
            System.out.println("Source and destination cannot be the same.");
            return;
        }

        int maxFlow = FordFulkerson.maxFlow(capacity, source - 1, sink - 1);

        System.out.println("\n==============================================");
        System.out.println("Source Vertex      : " + source);
        System.out.println("Destination Vertex : " + sink);
        System.out.println("Maximum Citation Flow = " + maxFlow);
        System.out.println("==============================================");
    }

    private static void runEdmondsKarpDemo(Scanner scanner) {
        System.out.println("\n========== EDMONDS-KARP ==========");

        System.out.print("\nEnter number of vertices: ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        if (n < 2) {
            System.out.println("Please enter at least 2 vertices.");
            return;
        }

        int[][] capacity = new int[n][n];

        System.out.print("\nEnter number of directed edges: ");
        int edges = Integer.parseInt(scanner.nextLine().trim());

        if (edges < 0) {
            System.out.println("Number of edges cannot be negative.");
            return;
        }

        for (int i = 1; i <= edges; i++) {
            while (true) {
                System.out.println("\nEdge " + i);

                System.out.print("Enter source vertex: ");
                int from = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter destination vertex: ");
                int to = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter capacity: ");
                int cap = Integer.parseInt(scanner.nextLine().trim());

                if (from < 1 || from > n || to < 1 || to > n) {
                    System.out.println("Invalid vertex. Enter values from 1 to " + n + ".");
                    continue;
                }

                if (from == to) {
                    System.out.println("Source and destination must be different.");
                    continue;
                }

                if (cap <= 0) {
                    System.out.println("Capacity must be greater than 0.");
                    continue;
                }

                capacity[from - 1][to - 1] += cap;
                break;
            }
        }

        System.out.print("\nEnter source vertex: ");
        int source = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter destination vertex: ");
        int sink = Integer.parseInt(scanner.nextLine().trim());

        if (source < 1 || source > n || sink < 1 || sink > n) {
            System.out.println("Invalid source or destination vertex.");
            return;
        }

        if (source == sink) {
            System.out.println("Source and destination cannot be the same.");
            return;
        }

        int maxFlow = EdmondsKarp.maxFlow(capacity, source - 1, sink - 1);

        System.out.println("\n==============================================");
        System.out.println("Source Vertex      : " + source);
        System.out.println("Destination Vertex : " + sink);
        System.out.println("Maximum Flow       : " + maxFlow);
        System.out.println("==============================================");
    }

    private static void runBipartiteMatchingDemo(Scanner scanner) {
        System.out.println("\n========== BIPARTITE MATCHING ==========");

        System.out.print("\nEnter number of articles (left-side vertices): ");
        int left = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter number of keywords (right-side vertices): ");
        int right = Integer.parseInt(scanner.nextLine().trim());

        if (left <= 0 || right <= 0) {
            System.out.println("Numbers must be greater than 0.");
            return;
        }

        boolean[][] graph = new boolean[left][right];

        System.out.println("\nEnter each article-keyword connection separately.");
        System.out.println("Enter 1 if the article can be matched with the keyword, otherwise 0.");

        for (int i = 0; i < left; i++) {
            for (int j = 0; j < right; j++) {
                while (true) {
                    System.out.print("Article " + (i + 1) +
                            " -> Keyword " + (j + 1) + " (0/1): ");
                    int value = Integer.parseInt(scanner.nextLine().trim());

                    if (value == 0 || value == 1) {
                        graph[i][j] = value == 1;
                        break;
                    }

                    System.out.println("Please enter only 0 or 1.");
                }
            }
        }

        int[] matches = BipartiteMatching.getMatching(graph);
        int count = 0;

        for (int match : matches) {
            if (match != -1) {
                count++;
            }
        }

        System.out.println("\n==============================================");
        System.out.println("Maximum Matching = " + count);
        System.out.println("\nMatched Pairs:");

        for (int i = 0; i < matches.length; i++) {
            if (matches[i] != -1) {
                System.out.println("Article " + (i + 1) +
                        " -> Keyword " + (matches[i] + 1));
            }
        }

        System.out.println("==============================================");
    }

    private static void runBitmaskDemo() {
        System.out.println("\n========== BITMASK DP ==========");

        int[][] distance = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        int answer = BitmaskDP.tsp(distance);
        System.out.println("Minimum optimized path cost: " + answer);
    }
}
