@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -d out src\model\Article.java src\repository\ArticleRepository.java src\repository\CorpusLoader.java src\search\NaiveSearch.java src\search\KMP.java src\search\RabinKarp.java src\search\ZFunction.java src\search\FuzzySearch.java src\similarity\SimilarityAnalyzer.java src\similarity\SuffixProcessor.java src\dynamicprogramming\EditDistance.java src\dynamicprogramming\SequenceAlignment.java src\dynamicprogramming\BitmaskDP.java src\graph\FordFulkerson.java src\graph\EdmondsKarp.java src\graph\BipartiteMatching.java src\query\QueryProcessor.java src\TextHack.java
if errorlevel 1 (
    echo.
    echo Compilation failed.
    pause
    exit /b 1
)
java -cp out TextHack
pause
