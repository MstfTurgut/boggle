import edu.princeton.cs.algs4.LinearProbingHashST;

public class Node
{
    private boolean isComplete;
    private final LinearProbingHashST<Character, Node> next = new LinearProbingHashST<>();

    /**
     * Getter method. Returns {@code true} if this node corresponds a valid word.
     *
     * @return instance variable {@code isComplete}.
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Setter method.
     *
     * @param complete parameter for instance variable
     */
    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    /**
     * Getter method. Returns the hashtable of nodes that is reachable from this node.
     *
     * @return instance variable {@code next}
     */
    public LinearProbingHashST<Character, Node> next() {
        return next;
    }
}