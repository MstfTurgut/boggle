import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import java.util.ArrayList;
import java.util.HashSet;

public class BoggleSolver {

    private final BoggleTrie boggleTrie;



    public BoggleSolver(String[] dictionary) {
        // Initialize the BoggleTrie and populate it with valid dictionary words.
        this.boggleTrie = new BoggleTrie();

        for (String s : dictionary) {
            if (s.length() >= 3) boggleTrie.put(s);
        }
    }



    // !WARNING! : I KNOW THE METHODS IN THIS CLASS LOOKS UGLY.
    // THIS CLASS AIMS FOR RAW SPEED. AND THERE IS NO MEMORY LIMIT.
    // SO I USED VERY COMPLEX DATA STRUCTURES TO GET A GOOD SPEED PERFORMANCE.
    // ALL THE FOR LOOPS AND IF STATEMENTS TO READ DATA FROM THESE DATA STRUCTURES.
    // SO AGAIN, I KNOW THE METHODS IN THIS CLASS LOOKS UGLY. BUT THIS IS THE INTENTION.



    /**
     *
     * Computes all the words in the given boggle board.
     *
     * @param board Boggle board to search.
     * @return All valid words from given boggle board as iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        boolean[] visited = new boolean[board.cols() * board.rows()];
        Node root = boggleTrie.getRoot();
        StringBuilder sb = new StringBuilder();
        HashSet<String> validWords = new HashSet<>();
        ArrayList<LinearProbingHashST<Character, Bag<Integer>>> adjProbe = new ArrayList<>(board.cols() * board.rows());

        // Create adjacency mappings for each dice on the board.
        for (int i = 0; i < board.cols() * board.rows(); i++) {

            LinearProbingHashST<Character, Bag<Integer>> adjMap = new LinearProbingHashST<>();

            // Populate adjacency mappings for the current dice.
            for (int j : getAdjDiceNumbers(i, board.cols(), board.rows())) {

                char adjChar = board.getLetter(j / board.cols(), j % board.cols());

                Bag<Integer> adjBag = adjMap.get(adjChar);

                if (adjBag == null) adjBag = new Bag<>();

                adjBag.add(j);
                adjMap.put(adjChar, adjBag);
            }

            adjProbe.add(adjMap);
        }

        // Explore each dice on the board to find valid words.
        for (int i = 0; i < board.rows() * board.cols(); i++) {
            char letter = board.getLetter(i / board.cols(), i % board.cols());
            if (root != null && root.next().get(letter) != null) {
                searchValidWords(root.next().get(letter), letter, i, visited, sb, validWords, adjProbe);
            }
        }

        return validWords;
    }



    // !WARNING! : I KNOW THE METHODS IN THIS CLASS LOOKS UGLY.
    // THIS CLASS AIMS FOR RAW SPEED. AND THERE IS NO MEMORY LIMIT.
    // SO I USED VERY COMPLEX DATA STRUCTURES TO GET A GOOD SPEED PERFORMANCE.
    // ALL THE FOR LOOPS AND IF STATEMENTS TO READ DATA FROM THESE DATA STRUCTURES.
    // SO AGAIN, I KNOW THE METHODS IN THIS CLASS LOOKS UGLY. BUT THIS IS THE INTENTION.



    // Searches the trie recursively to look for valid words and saves them.
    private void searchValidWords(Node current, char letter, int diceNumber, boolean[] visited, StringBuilder sb, HashSet<String> validWords, ArrayList<LinearProbingHashST<Character, Bag<Integer>>> adjProbe) {
        visited[diceNumber] = true;

        if (letter == 'Q') {
            sb.append("QU");
            current = current.next().get('U');
            if (current == null) {
                // If there is no 'U' after 'Q', terminate the search.
                visited[diceNumber] = false;
                sb.delete(sb.length() - 2, sb.length());
                return;
            }
        } else {
            sb.append(letter);
        }

        if (current.isComplete()) validWords.add(sb.toString());

        if (current.next().keys() != null) {
            for (char c : current.next().keys()) {
                Bag<Integer> adjBag = adjProbe.get(diceNumber).get(c);
                if (adjBag != null) {
                    for (int i : adjBag) {
                        if (!visited[i]) {
                            searchValidWords(current.next().get(c), c, i, visited, sb, validWords, adjProbe);
                        }
                    }
                }
            }
        }

        visited[diceNumber] = false;
        int length = (letter == 'Q') ? 2 : 1;
        sb.delete(sb.length() - length, sb.length());
    }



    // !WARNING! : I KNOW THE METHODS IN THIS CLASS LOOKS UGLY.
    // THIS CLASS AIMS FOR RAW SPEED. AND THERE IS NO MEMORY LIMIT.
    // SO I USED VERY COMPLEX DATA STRUCTURES TO GET A GOOD SPEED PERFORMANCE.
    // ALL THE FOR LOOPS AND IF STATEMENTS TO READ DATA FROM THESE DATA STRUCTURES.
    // SO AGAIN, I KNOW THE METHODS IN THIS CLASS LOOKS UGLY. BUT THIS IS THE INTENTION.



    // support method for the board solver methods
    private Iterable<Integer> getAdjDiceNumbers(int diceNumber, int boardWidth, int boardHeight) {
        Bag<Integer> neighbors = new Bag<>();

        if (diceNumber % boardWidth != 0) {
            neighbors.add(diceNumber - 1);
            if (diceNumber / boardWidth != 0) neighbors.add(diceNumber - boardWidth - 1);
            if (diceNumber / boardWidth != boardHeight - 1) neighbors.add(diceNumber + boardWidth - 1);
        }
        if (diceNumber % boardWidth != boardWidth - 1) {
            neighbors.add(diceNumber + 1);
            if (diceNumber / boardWidth != 0) neighbors.add(diceNumber - boardWidth + 1);
            if (diceNumber / boardWidth != boardHeight - 1) neighbors.add(diceNumber + boardWidth + 1);
        }
        if (diceNumber / boardWidth != 0) {
            neighbors.add(diceNumber - boardWidth);
        }
        if (diceNumber / boardWidth != boardHeight - 1) {
            neighbors.add(diceNumber + boardWidth);
        }

        return neighbors;
    }




    /**
     * Score calculator.
     *
     * @param word Word to calculate the score of.
     * @return The score of given word.
     */
    public int scoreOf(String word) {

        if (!boggleTrie.contains(word)) {
            return 0;
        } else {
            int length = word.length();
            if (length <= 4) {
                return 1;
            } else if (length == 5) {
                return 2;
            } else if (length == 6) {
                return 3;
            } else if (length == 7) {
                return 5;
            } else {
                return 11;
            }
        }
    }




    // Unit testing
    public static void main(String[] args) {
        // Read dictionary from file and initialize BoggleSolver.
        In in = new In("dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        char[][] board =  {
                { 'D', 'O', 'T', 'Y' },
                { 'T', 'R', 'S', 'F' },
                { 'M', 'X', 'M', 'O' },
                { 'Z', 'A', 'B', 'W' }
        };

        // Find and display all valid words on the board, along with the total score.
        int score = 0;
        for (String word : solver.getAllValidWords(new BoggleBoard(board))) {
            System.out.println(word);
            score += solver.scoreOf(word);
        }
        System.out.println("Score = " + score);

        System.out.println("\n-----------------------------\n");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            solver.getAllValidWords(new BoggleBoard());
        }
        long end = System.currentTimeMillis();

        System.out.println("Passed time to solve 5000 boggle boards : " + (end - start));

    }
}
