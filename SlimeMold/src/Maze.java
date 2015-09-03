import java.util.Map;

public class Maze<K,V> {
    private int nodeCounter;
    private boolean isAdded;
    private Node[] maze;
    private int pointer;

    private class Node<K,V> implements Map.Entry<K, V>, Comparable {
        private K k;
        private V v;
        private boolean isTraversed;

        public Node(K k, V v, boolean isTraversed) {
            this.k = k;
            this.v = v;
            this.isTraversed = isTraversed;
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            V old = v;
            v = value;
            return old;
        }
    }

    public Maze() {
        Organism organism = new Organism();

        final int INITIAL_SIZE = 15;
        nodeCounter = 0;
        isAdded = false;
        maze = new Node[INITIAL_SIZE];
        pointer = 1;
        for(int i = 0; i < INITIAL_SIZE; i++) {
            maze[i] = new Node(null, null, false);
        }
    }

    public boolean addLeft() {
        Node<K,V> newNode = new Node<>(null, null, false);
        if(pointer != 0 || pointer < maze.length) {
            if(!maze[pointer * 2].isTraversed) {
                newNode.isTraversed = true;
                maze[2 * pointer] = newNode;
                pointer = pointer * 2;
            }
        }
        return false;
    }

    public boolean addRight() {
        Node<K,V> newNode = new Node<>(null, null, false);
        if(pointer != 0 || pointer < maze.length) {
            if(!maze[pointer * 2 + 1].isTraversed) {
                newNode.isTraversed = true;
                maze[2 * pointer + 1] = newNode;
                pointer = pointer * 2 + 1;
            }
        }
        return false;
    }

    public void printTree() {
        for(int i = 1; i < maze.length; i++) {
            System.out.println("Index: " + i + " " + maze[i].isTraversed);
        }
    }
}
