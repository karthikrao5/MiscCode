
/**
 * @author Karthik
 * @version 1.0
 * Binary Search Tree implementation
 * CS 1332 Homework #3
 * Summer 2015
 */


import java.util.*;

public class BST<E extends Comparable<? super E>> implements BinaryTree<E>{

    private Node<E> root, minNodeForDeletion;
    private int nodeCounter;
    private List<E> orderedList;
    private Queue<Node<E>> discovered, visited;
    private E max,min;
    private Boolean isAdded, isFound;

    private class Node<E extends Comparable<? super E>>{

        private E data;
        private Node<E> left, right;

        public Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private class BSTIterator implements Iterator<E>{

        private Node<E> next;
        private Stack<Node<E>> stack;

        public BSTIterator(Node<E> root) {
            stack = new Stack<Node<E>>();
            while( root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext(){
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            Node<E> node = stack.pop();

            E val = node.data;

            if(node.right != null) {
                node = node.right;

                while(node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return val;
        }
    }

    public BST() {
        root = null;
        nodeCounter = 0;
        orderedList = new ArrayList<>();
        max = null;
    }

    /**
     * Adds the item to the tremee.  Duplicate items and null items should not be added.
     * Runs in O(log n) expected time, may be linear time in worst case
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {

        if(item != null) {
            if(root != null) {
                add(root, item);
                return isAdded;
            } else {
                root = new Node<>(item, null, null);
                nodeCounter++;
                return true;
            }
        } else {
            return false;
        }

    }


    /**
     * Private helper method for recursive add
     * @param node node to add
     * @param item data to add
     * @return node
     */
    private Node<E> add(Node<E> node, E item) {
        isAdded = false;
        Node<E> newNode = new Node<>(item, null, null);

        if(node == null) {
            isAdded = true;
            nodeCounter++;
            return newNode;
        }

        if(item.compareTo(node.data) > 0) {
            node.right = add(node.right, item);
        } else if(item.compareTo(node.data) < 0) {
            node.left = add(node.left, item);
        } else {
            isAdded = false;
        }
        return node;
    }

    /**
     * returns the maximum element held in the tree.  null if tree is empty.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @return maximum item or null if empty
     */
    public E max(){
        if(root == null) {
            return null;
        } else {
            return max(root).data;
        }
    }

    private Node<E> max(Node<E> node) {
        if(node == null) {
            return null;
        } else if(node.right == null) {
            return node;
        }
        return max(node.right);
    }

    /**
     * returns the number of items in the tree
     * runs in O(log n)
     *
     * return
     */
    public int size(){
        return nodeCounter;
    }

    /**
     * runs in O(1)2
     *
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty(){
        return nodeCounter == 0;
    }

    /**
     * runs in O(log n)
     * @return the minimum element in the tree or null if empty
     */
    public E min(){

        if(root != null) {
           return min(root);
        }
        return null;
    }

    private E min(Node<E> node) {

        if(node == null) {
            return null;
        } else if(node.left == null) {
            return node.data;
        } else {
            return min(node.left);
        }
    }

    /**
     * Checks for the given item in the tree.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @param item the item to look for
     * @return true if item is in tree, false otherwise
     */
    public boolean contains(E item) {
        if(item != null) {
            if(root != null) {
                return contains(root, item);
            }
        }
        return false;
    }

    private boolean contains(Node<E> node, E item) {
        isFound = false;

        if(node == null) {
            return false;
        }
        if(node.data.compareTo(item) < 0) {
            return contains(node.right, item);
        } else if(node.data.compareTo(item) > 0) {
            return contains(node.left, item);
        } else {
            return true;
        }
    }


    /**
     * removes the given item from the tree
     * runs in O(log n) expected, may be linear in worst case
     * use in-order successor if necessary
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */

    public boolean remove(E item) {

        Node<E> pointer = root;
        Node<E> parent = null, minNode = null;

        if (item == null) {
            return false;
        } else if (pointer == null) {
            return false;

        //if root is selected to be removed
        } else if (root.data.compareTo(item) == 0 && root.right != null) {
            System.out.println("hello");
            Node<E> temp = root;
            temp = temp.right;

            pointer = temp;
            while (temp.left != null) {
                if (temp.left.data.compareTo(temp.data) < 0) {
                    temp = temp.left;
                }
            }
            if (temp.left == null) {
                root.data = temp.data;

                System.out.println("element: " + pointer.data.toString());

                pointer.left = null;
                System.out.println("element: " + pointer.data.toString());

                System.out.println("element: " + pointer.data.toString());

                root.right = temp.right;
                System.out.println("element: " + pointer.right.data.toString());

                nodeCounter--;
                root = pointer;
                return true;

            }
        } else if(nodeCounter == 1) {
            root = null;
            nodeCounter--;
            return true;
        } else {
            pointer = root;

            while(pointer != null) {
                if(item.compareTo(pointer.data) > 0) {
                    parent = pointer;

                    pointer = pointer.right;
                } else if(item.compareTo(pointer.data) < 0) {
                    parent = pointer;

                    pointer = pointer.left;

                    //if node is found, do this:
                } else if(item.compareTo(pointer.data) == 0) {
                    //System.out.println("Node is found!");
                    // System.out.println("Parent is: " + parent.data.toString());
                    // System.out.println("Pointer is: " + pointer.data.toString());
                    break;
                }
            }

            //Everything below will execute when else statement is executed.

            if(pointer == null) {
                //If search query is not in the tree. return false;
                return false;
            }

            //Case 1: Leaf node
            if(pointer.right == null && pointer.left == null) {
               // System.out.println("Leaf node to be deleted: " + pointer.data.toString());
                if(parent.data.compareTo(pointer.data) > 0) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                nodeCounter--;
                return true;

            //Case 2.0: One subtree and its Left
            } else if(pointer.right == null && pointer.left != null) {
                //System.out.println("one subtree node to be deleted: " + pointer.data.toString());

                parent.left = pointer.left;
                nodeCounter--;
                return true;
            //Case 2.1: One subtree and its right
            } else if(pointer.right != null && pointer.left == null) {
                //System.out.println("One subtree node to be deleted: " + pointer.data.toString());

                parent.right = pointer.right;
                nodeCounter--;
                return true;
            }

            //Case 3: 2 subtrees exist
            if(pointer.left != null && pointer.right != null) {
                //System.out.println("hello");
                Node<E> temp = pointer;
                temp = temp.right;
                while (temp.left != null) {
                    if(pointer.left.data.compareTo(pointer.data) < 0){
                        temp = temp.left;
                    }
                }
                if(temp.left == null) {
                    minNode = temp;
                }
                pointer.data = minNode.data;
                pointer.right = minNode.right;
                nodeCounter--;
                return true;
            }
        }
        return false;
    }

    /**
     * returns an iterator over this collection
     *
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator(){

        BSTIterator iterate = new BSTIterator(root);
        return iterate;
    }

    /**
     * Runs in linear time
     * @return a list of the data in post-order traversal order
     */
    public List<E> getPostOrder(){
        Node<E> curr = root;

        getPostOrder(curr);
        //System.out.println("PostOrder traversal: " + orderedList.toString());
        return orderedList;
    }

    private Node<E> getPostOrder(Node<E> node) {
        if (node == null) {
            return null;
        } else {
            getPostOrder(node.left);
            getPostOrder(node.right);
            orderedList.add(node.data);
            return null;
        }
    }

        /**
         * Runs in linear time
         * @return a list of the data in level-order traversal order
         */
    public List<E> getLevelOrder(){
        List<E> temp = new LinkedList<>();

        visited = new LinkedList<>();

        if(root == null) {
            return null;
        } else {
            visited.add(root);

            while(!visited.isEmpty()) {
                Node<E> curr = visited.peek();

                if(curr.left != null) {
                    visited.add(curr.left);
                }
                if(curr.right != null) {
                    visited.add(curr.right);
                }
                temp.add(visited.peek().data);
                visited.remove();
            }
        }
        return temp;
    }

    /**
     * Runs in linear time
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getPreOrder(){
        Node<E> curr = root;

        getPreOrder(curr);
        //System.out.println("PreOrder traversal: " + orderedList.toString());
        return orderedList;
    }

    private Node<E> getPreOrder(Node<E> node) {
        if (node == null) {

            return null;
        } else {
            orderedList.add(node.data);
            getPreOrder(node.left);
            getPreOrder(node.right);
        }
        return null;
    }

    /**
     * Runs in linear time
     * @return a list of the data in pre-order traversal order
     * LNR
     */
    public List<E> getInOrder(){
        Node<E> curr = root;
        getInOrder(curr);
        //System.out.println("In Order traversal: " + orderedList.toString());
        return orderedList;
    }

    private Node<E> getInOrder(Node<E> node) {

        if(node == null) {
            return null;
        } else {
            getInOrder(node.left);
            orderedList.add(node.data);
            getInOrder(node.right);
        }
        return null;
    }

    /**
     * Runs in linear time
     * Removes all the elements from this tree
     */
    public void clear(){

        if(root != null) {
            root.left = null;
            root.right = null;
            nodeCounter = 0;
        }
    }
}
