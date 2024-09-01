

/***
 *
 * @Author Kapil Sharma ks4643
 * Foundations of Artificial Intelligence
 * Homework 1-P
 * This program uses BFS to perform all the necessary action
 ***/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class hw1 {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Provide valid arguments");
            return;
        }

        String dictionaryPath = args[0];
        String firstWord = args[1];
        String secondWord = args[2];

        try {
            Set<String> words = readDictionary(dictionaryPath);
            List<String> wordList = filterWords(words, firstWord.length());
            Map<String, List<String>> graph = simulateGraph(wordList);

            List<String> shortestPath = bfs(graph, firstWord, secondWord);

            if (shortestPath != null) {
                for (String word : shortestPath) {
                    System.out.println(word);
                }
            } else {
                System.out.println("No solution");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> readDictionary(String filePath) throws IOException {
        Set<String> words = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim().toLowerCase());
        }
        reader.close();
        return words;
    }

    private static List<String> filterWords(Set<String> words, int length) {
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() == length) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    private static Map<String, List<String>> simulateGraph(List<String> wordList) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String word : wordList) {
            graph.put(word, new ArrayList<>());
            for (String nextWord : wordList) {
                if (letterComparisons(word, nextWord)) {
                    graph.get(word).add(nextWord);
                }
            }
        }
        return graph;
    }

    private static boolean letterComparisons(String word1, String word2) {
        int difference = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                difference++;
            }
        }
        return difference == 1;
    }

    private static List<String> bfs(Map<String, List<String>> graph, String start, String end) {
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        Map<String, String> predecessor = new HashMap<>();
        predecessor.put(start, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                break;
            }

            for (String neighbor : graph.get(current)) {
                if (!predecessor.containsKey(neighbor)) {
                    predecessor.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (predecessor.containsKey(end)) {
            List<String> path = new ArrayList<>();
            String current = end;
            while (current != null) {
                path.add(0, current);
                current = predecessor.get(current);
            }
            return path;
        } else {
            return null;
        }
    }
}
