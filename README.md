# TextHack - Wikipedia Text Search & Analysis

## Dataset

Keep your Wikipedia corpus at:

D:\Wikipedia

Expected structure may contain:

D:\Wikipedia
├── 1of2
├── 2of2
└── AllCombined.txt

The program recursively scans the directory and reads files whose names begin with `wiki_` or end with `.txt`.

## Experiments

1. Corpus Loading and Article Repository Creation - CO1
2. Query Processing and Article Retrieval Framework - CO1
3. Pattern Matching: Naive, KMP, Rabin-Karp and Z Function - CO2
4. Rabin-Karp, Document Similarity and Suffix-Based Processing - CO2
5. Edit Distance and Fuzzy Search using Wagner-Fischer - CO3
6. Sequence Alignment and Bitmask Dynamic Programming - CO3
7. Flow Networks and Ford-Fulkerson for Citation Flow Analysis - CO4
8. Edmonds-Karp and Bipartite Matching Applications - CO4

## Pattern Matching algorithms

The pattern-matching module now includes all four requested algorithms:

- Naive Pattern Matching
- Knuth-Morris-Pratt (KMP)
- Rabin-Karp (often misspelled "Robin Karp")
- Z Function / Z Algorithm

The menu compares the occurrence positions and execution time of all four algorithms on the same article and pattern.

## Graph algorithms

### Experiment 7 - Ford-Fulkerson
The program accepts a directed capacity graph, source and sink, then computes maximum flow. The edges can represent citation-flow links between articles.

### Experiment 8 - Edmonds-Karp
The program computes maximum flow using BFS to select augmenting paths.

### Bipartite Matching
The program accepts a left/right adjacency matrix and computes a maximum matching using augmenting paths. This can model applications such as article-to-keyword, article-to-topic, student-to-course, or task-to-resource matching.

## Compile

From the TextHack folder:

javac -d out src\model\Article.java src\repository\ArticleRepository.java src\repository\CorpusLoader.java src\search\NaiveSearch.java src\search\KMP.java src\search\RabinKarp.java src\search\ZFunction.java src\search\FuzzySearch.java src\similarity\SimilarityAnalyzer.java src\similarity\SuffixProcessor.java src\dynamicprogramming\EditDistance.java src\dynamicprogramming\SequenceAlignment.java src\dynamicprogramming\BitmaskDP.java src\graph\FordFulkerson.java src\graph\EdmondsKarp.java src\graph\BipartiteMatching.java src\query\QueryProcessor.java src\TextHack.java

## Run

java -cp out TextHack

Or double-click `run.bat` on Windows.

## Menu

1. View Article Repository
2. Pattern Matching - Naive / KMP / Rabin-Karp / Z
3. Keyword Search - KMP
4. Fuzzy Search - Edit Distance
5. Document Similarity
6. Sequence Alignment
7. Edit Distance
8. Bitmask DP
9. Suffix Processing
10. Flow Network - Ford-Fulkerson
11. Flow Network - Edmonds-Karp
12. Bipartite Matching
0. Exit

## Important

The Wikipedia article parser is a teaching-oriented heuristic for the provided plain-text corpus. It is intended to demonstrate the requested DSA algorithms and search workflow rather than to be a complete Wikipedia dump parser.
