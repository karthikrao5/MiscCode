import java.util.*;

/**
 * @author karthik rao
 * @version 1.0
 * Huffman Coding EC homework
 * Summer 2015
 * CS 1332
 */

public class HuffmanEncoder {


    /**
     * Contains a map of chars to the number of recurrences in the
     * message
     */
    private Map<Character, Integer> freqmap;


    /**
     * Contains a map of the chars to its binary key. Basically
     * a decryption/encryption key
     */
    private Map<Character, String> binaryMap;


    /**
     * A priority queue of Nodes used for building the tree
     */
    private PriorityQueue<Node> queue;
    private StringBuilder sbEncoded, sbDecoded;
    private Node root;

    private class Node implements Comparable<Node>{

        private char c;
        private int freq;
        private Node left, right;

        public Node(Node left, Node right, char c, int freq) {
            this.left = left;
            this.right = right;
            this.c = c;
            this.freq = freq;
        }
        @Override
        public int compareTo(Node o) {
            if(this.freq > o.freq) {
                return 1;
            } else if(this.freq < o.freq) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Constructor
     */
    public HuffmanEncoder() {
        queue = new PriorityQueue<>(10, (o1, o2) -> {
            if(o1 != null && o2 != null) {
                Integer n1 = (Integer) ((Node) o1).freq;
                Integer n2 = (Integer) ((Node) o2).freq;
                return n1.compareTo(n2);
            }
            return 0;
        });
        sbDecoded = new StringBuilder();
        sbEncoded = new StringBuilder();
    }

    /**
     * Step 1
     * Builds a frequency map of the chars in the unencrypted message
     * @param text The unencoded string to compress
     * @return freqmap map of char frequencies
     */
    public Map<Character, Integer> freqMap(String text) {
        freqmap = new LinkedHashMap<Character, Integer>();

        for (int i = 0; i < text.length() ; i++) {
            if (!freqmap.containsKey(text.charAt(i))) {
                freqmap.put(text.charAt(i), 1);
            } else {
                freqmap.put(text.charAt(i), freqmap.get(text.charAt(i)) + 1);
            }
        }
        return freqmap;
    }

    /**
     * Builds a complete binary tree
     * @param freqmap map of chars and amount of recurrences
     * @return
     */
    public Node buildTree(Map<Character, Integer> freqmap) {
        //Step 2: build the tree by combining the least frequency leaf
        //nodes until a root is created.

        //to generate a tree, pick the two smallest frequencies
        //and create a parent node that is the sum of the two smallest ones

        //Create leaf nodes with every char
        for(Map.Entry<Character, Integer> entry : freqmap.entrySet()) {
            Node n = new Node(null, null, entry.getKey(), entry.getValue());
            queue.add(n);
        }

        //combine the lowest frequency leaves into parent nodes and put back on
        //the queue. Since its a priority queue, the lowest two will always be
        //selected, which is at the core of the huffman algorithm.
        while(queue.size() > 1) {
            Node n1 = queue.remove();
            Node n2 = queue.remove();
            Node parent = new Node(n1, n2, '\0', n1.freq + n2.freq);
            queue.add(parent);
        }

        root = queue.peek();
        //returns the root node
        return queue.remove();
    }

    /**
     * Generates the binary key for which to encode and decode the message
     * @param root root of huffman tree
     * @return binaryMap of the chars and their binary string key
     */
    public Map<Character, String> binaryCodeGen(Node root) {
        //I used a linkedhashmap to preserve insertion order
        binaryMap = new LinkedHashMap<Character, String>();
        traverse(root, "");
        //System.out.println(binaryMap.toString());
        return binaryMap;
    }

    /**
     * Private method for recursive tree traversal
     * @param node node to traverse from
     * @param str the string that is appended
     */
    private void traverse(Node node, String str) {
        //Step 3: Once a root is created, traverse the tree and assign a binary code
        //for each letter in the message

        if(node.left == null && node.right == null) {
            binaryMap.put(node.c, str);
            return;
        }
        traverse(node.right, str + "1");
        traverse(node.left, str + "0");
    }


    /**
     * encodes a given text and returns the encoded string
     * @param text to encode/compress
     * @return the encoded string
     */
    public String encode(String text) {
        //Step 4: Create the encoded string by retrieving the binary string
        //that represents each letter in the string and append it to the encoded
        //message string and return it.

        Map<Character, String> keyCode = binaryCodeGen(buildTree(freqMap(text)));

        for (int i = 0; i < text.length(); i++) {
            sbEncoded.append(keyCode.get(text.charAt(i)));
        }
        return sbEncoded.toString();
    }


    /**
     * Decodes a decrypted string with the given map.
     * @param map containing the chars and their binary equivalents
     * @param es encoded string in binary digits
     * @return decoded string
     */
    public String decode(Map<Character, String> map, String es) {

        System.out.println("Key: " + map);
        String temp = "";

        for (int i = 0; i < es.length(); i++) {
            temp = temp + es.charAt(i);

            if (map.containsValue(temp)) {
                for (Map.Entry<Character, String> entry : binaryMap.entrySet()) {
                    if(entry.getValue().equals(temp)) {
                        sbDecoded.append(entry.getKey());
                        temp = "";
                        break;
                    }
                }
            }
        }
        return sbDecoded.toString();
    }
}
