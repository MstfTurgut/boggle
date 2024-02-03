import edu.princeton.cs.algs4.LinearProbingHashST;

public class Node
{
    private boolean isComplete;
    private final LinearProbingHashST<Character, Node> next = new LinearProbingHashST<>();

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public LinearProbingHashST<Character, Node> next() {
        return next;
    }
}