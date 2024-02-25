import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class BoggleBoard {
    // the 16 Boggle dice
    private static final String[] BOGGLE_BOARD = {
            "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
            "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
            "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
            "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
    };

    // letters and frequencies of letters in the English alphabet
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private final int m;        // number of rows
    private final int n;        // number of columns
    private final char[][] board;     // the m-by-n array of characters

    /**
     * Initializes a random 4-by-4 board, by rolling the dice.
     */
    public BoggleBoard() {
        m = 4;
        n = 4;
        StdRandom.shuffle(BOGGLE_BOARD);
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String letters = BOGGLE_BOARD[n*i+j];
                int r = StdRandom.uniformInt(letters.length());
                board[i][j] = letters.charAt(r);
            }
        }
    }

    /**
     * Initializes a board from the given 2d character array,
     * with 'Q' representing the two-letter sequence "Qu".
     * @param a the 2d character array
     */
    public BoggleBoard(char[][] a) {
        this.m = a.length;
        if (m == 0) throw new IllegalArgumentException("number of rows must be a positive integer");
        this.n = a[0].length;
        if (n == 0) throw new IllegalArgumentException("number of columns must be a positive integer");
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            if (a[i].length != n)
                throw new IllegalArgumentException("char[][] array is ragged");
            for (int j = 0; j < n; j++) {
                if (ALPHABET.indexOf(a[i][j]) == -1)
                    throw new IllegalArgumentException("invalid character: " + a[i][j]);
                board[i][j] = a[i][j];
            }
        }
    }

    /**
     * Returns the number of rows.
     * @return number of rows
     */
    public int rows() {
        return m;
    }

    /**
     * Returns the number of columns.
     * @return number of columns
     */
    public int cols() {
        return n;
    }

    /**
     * Returns the letter in row i and column j,
     * with 'Q' representing the two-letter sequence "Qu".
     * @param i the row
     * @param j the column
     * @return the letter in row i and column j
     *    with 'Q' representing the two-letter sequence "Qu".
     */
    public char getLetter(int i, int j) {
        return board[i][j];
    }

    /**
     * Returns a string representation of the board, replacing 'Q' with "Qu".
     * @return a string representation of the board, replacing 'Q' with "Qu"
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(m + " " + n + "\n");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
                if (board[i][j] == 'Q') sb.append("u ");
                else sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Unit tests the BoggleBoard data type.
     */
    public static void main(String[] args) {

        // initialize a 4-by-4 board from a 2d char array
        StdOut.println("4-by-4 board from 2D character array:");
        char[][] a =  {
                { 'D', 'O', 'T', 'Y' },
                { 'T', 'R', 'S', 'F' },
                { 'M', 'X', 'M', 'O' },
                { 'Z', 'A', 'B', 'W' }
        };
        BoggleBoard board = new BoggleBoard(a);
        StdOut.println(board);
        StdOut.println();

    }

}