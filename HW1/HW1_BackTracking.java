/**
 *
 * @Author Kapil Sharma ks4643
 *
 *
 * This program uses backtracking to find the shortest path
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



class Node{
    String word;
    Node parent;

    public Node(String word, Node parent) {
        this.word = word;
        this.parent = parent;
    }
}

public class HW1_BackTracking {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> wordList;

        if(args.length == 3) {
            String dictionary = args[0];
            String startWord = args[1];
            String targetWord = args[2];
            wordList = backTracking(findWords(dictionary, startWord, targetWord));

            if (wordList != null) {
                for( int i = wordList.size() - 1 ; i >= 0; i-- )
                    System.out.println( wordList.get(i) );
            }
            else
                System.out.println( "No Solution" );
        }

        //If no argument is provided then display message
        else {
            System.out.println( "Provide valid arguments" );
        }
    }

    /**
     * This function is used to find words from the dictionary
     *
     * @param   dict    A dictionary file
     * @param   start   Starting word
     * @param   target  Target word
     *
     *
     */
    public static Node findWords(String dict, String start,
                                     String target) throws FileNotFoundException {
        File file = new File(dict);

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start,null));

        while (!queue.isEmpty()){
            Node topWord;
            topWord = queue.remove();

            char [] wordArray = topWord.word.toCharArray();
            char [] targetWordArray = target.toCharArray();
            for (int i = 0; i < wordArray.length; i++) {
                if (wordArray[i] == targetWordArray[i])
                    continue;
                for (char character = 'a'; character <= 'z'; character++) {
                    char temp = wordArray[i];
                    if (wordArray[i] != character) {
                        wordArray[i] = character;
                    } else
                        continue;
                    String newWords = new String(wordArray);
                    Scanner scanner = new Scanner(file);

//                    while (scanner.hasNext()) {
//                        if (scanner.next().equals(newWords) ) {
//                            if(newWords.equals(target)) {
//                                return new Node(newWords, topWord);
//                            }
//                            if (!queue.contains(newWords)) {
//                                queue.add(new Node(newWords,topWord));
//                            }
//                        }
//                    }
                    wordArray[i] = temp;
                }
            }
        }
        return null;
    }

    /**
     * This function backtracks the Node to find the shortest path
     *
     * @param   Node   This is the target node
     *
     *
     */
    public static ArrayList<String> backTracking(Node Node){
        ArrayList<String> wordList = new ArrayList<>();
        while (Node.parent != null){
            wordList.add(Node.word);
            Node = Node.parent;
        }
        wordList.add(Node.word);
        return wordList;
    }

}
