public class BoggleTrie {

    private Node root;

    public void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) { // Change value associated with key if in subtrie rooted at x.
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.setComplete(true);
            return x; }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        x.next().put(c, put(x.next().get(c), key, d+1));
        return x;
    }

    public boolean contains(String key) {
        Node x = contains(root, key, 0);
        if (x == null) return false;
        return x.isComplete();
    }
    private Node contains(Node x, String key, int d) { // Return value associated with key in the subtrie rooted at x.
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return contains(x.next().get(c), key, d+1);
    }

    public Node getRoot() {
        return root;
    }

}
