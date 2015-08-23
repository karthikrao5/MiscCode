
/**
 * @author karthik
 * @version 1.0
 * CS 1332 Homework 4
 * AVL tree implementation
 */
import java.util.*;

public class AVLTree <E extends Comparable<? super E>> implements BSTree<E> {

    private Node<E> root, min;
    private int nodeCounter, maxInt;
    private Queue<Node<E>> visited;
    private boolean isAdded, isRemoved;
    private List<E> list;


    private class Node<E extends Comparable<? super E>> {

        private E data;
        private Node<E> left, right;
        private int nodeHeight;

        public Node(E data, Node<E> left, Node<E> right, int height) {
            this.data = data;
            this.left = left;
            this.right = right;
            height = nodeHeight;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public E getElement() {
            return data;
        }

        public int getNodeHeight(){
            return nodeHeight;
        }

        public void setLeft(Node<E> insert) {
            left = insert;
        }

        public void setRight(Node<E> insert) {
            right = insert;
        }

        public void setElement(E val) {
            data = val;
        }

        public void setHeight(int h) {
            nodeHeight = h;
        }

//        public int height(Node<E> node) {
//            if(node == null) {
//                return -1;
//            } else {
//                return node.nodeHeight;
//            }
//        }
//
//        public void setHeight(Node<E> node) {
//            int lh = height(node.left);
//            int rh = height(node.right);
//
//            if(lh < rh) {
//                node.nodeHeight = 1 + rh;
//            } else {
//                node.nodeHeight = 1 + lh;
//            }
//        }
    }

    private class AVLIterator implements Iterator<E> {

        private Node<E> next;
        private Stack<Node<E>> stack;

        public AVLIterator(Node<E> root) {
            stack = new Stack<Node<E>>();
            while(root != null) {
                stack.push(root);
                root = root.getLeft();
            }
        }

        @Override
        public boolean hasNext(){
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            Node<E> node = stack.pop();

            E val = node.getElement();

            if(node.getRight() != null) {
                node = node.getRight();

                while(node != null) {
                    stack.push(node);
                    node = node.getLeft();
                }
            }
            return val;
        }
    }


    public AVLTree() {
        root = null;
        nodeCounter = 0;
        list = new LinkedList<>();
    }

    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added. O(log n)
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {
        isAdded = false;

        if(item != null) {
            if(root == null) {
                root = new Node<>(item, null, null, 0);
                nodeCounter++;
                isAdded = true;
                return isAdded;
            } else {
                add(root, item);
                return isAdded;
            }
        }
        return isAdded;
    }

    public Node<E> add(Node<E> node, E item) {

        if(node == null) {
            nodeCounter++;
            isAdded = true;
            //System.out.println("Item added: " + item.toString());
            return new Node<>(item, null, null, 0);
        }

        if(item.compareTo(node.data) > 0) {
            node.right = add(node.right, item);

        } else if(item.compareTo(node.getElement()) < 0) {
            node.left = add(node.left, item);

        } else {
            isAdded = false;
        }

        node.nodeHeight = Math.max(height(node.left), height(node.right) + 1);

        if(heightDiff(node) > 1) {

            if (heightDiff(node.left) < 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            } else {
                return rotateRight(node);
            }

        } else if(heightDiff(node) < -1 ) {

            if (heightDiff(node.right) > 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            } else {
                return rotateLeft(node);
            }

        }
        return node;
    }


    /**
     * returns the maximum element held in the tree.  null if tree is empty.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @return maximum item or null if empty
     */

    //Max method completed.
    public E max() {
        if(root != null) {
            return max(root).getElement();
        }
        return null;
    }

    public Node<E> max(Node<E> node) {
        if(node == null) {
            return null;
        }

        if(node.right == null) {
            return node;
        }
        return max(node.right);
    }

    /**
     * runs in O(1)
     *
     * @return the minimum element in the tree or null if empty
     */

    public E min() {
        if(root != null) {
            return min(root).getElement();
        }
        return null;
    }

    public Node<E> min(Node<E> node) {
        if(node == null) {
            return null;
        }
        if(node.left == null) {
            return node;
        }
        return min(node.left);
    }

    /**
     * returns the number of items in the tree
     * runs in O(1)
     *
     * @return int
     */
    public int size() {
        return nodeCounter;
    }

    /**
     * runs in O(1)
     *
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        return nodeCounter == 0;
    }

    /**
     * Checks for the given item in the tree.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @param item the item to look for
     * @return true if item is in tree, false otherwise
     */
    public boolean contains(E item) {
        return (item != null || root != null) && contains(root, item);
    }

    public boolean contains(Node<E> node, E data) {
        if(node == null) {
            return false;
        } else if(data.compareTo(node.getElement()) > 0) {
            return contains(node.right, data);
        } else if(data.compareTo(node.getElement()) < 0) {
            return contains(node.left, data);
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
        } else if (root.getElement().compareTo(item) == 0 && root.getRight() != null) {
            System.out.println("hello");
            Node<E> temp = root;
            temp = temp.getRight();

            pointer = temp;
            while (temp.getLeft() != null) {
                if (temp.getLeft().getElement().compareTo(temp.getElement()) < 0) {
                    temp = temp.getLeft();
                }
            }
            if (temp.getLeft() == null) {


                root.setElement(temp.getElement());

                System.out.println("element: " + pointer.getElement().toString());

                pointer.setLeft(null);
                System.out.println("element: " + pointer.getElement().toString());

                System.out.println("element: " + pointer.getElement().toString());


                root.setRight(temp.getRight());
                System.out.println("element: " + pointer.getRight().getElement().toString());


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
                if(item.compareTo(pointer.getElement()) > 0) {
                    parent = pointer;

                    pointer = pointer.getRight();
                } else if(item.compareTo(pointer.getElement()) < 0) {
                    parent = pointer;

                    pointer = pointer.getLeft();

                    //if node is found, do this:
                } else if(item.compareTo(pointer.getElement()) == 0) {
                    //System.out.println("Node is found!");
                    // System.out.println("Parent is: " + parent.getElement().toString());
                    // System.out.println("Pointer is: " + pointer.getElement().toString());
                    break;
                }
            }

            //Everything below will execute when else statement is executed.

            if(pointer == null) {
                //If search query is not in the tree. return false;
                return false;
            }

            //Case 1: Leaf node
            if(pointer.getRight() == null && pointer.getLeft() == null) {
                // System.out.println("Leaf node to be deleted: " + pointer.getElement().toString());
                if(parent.getElement().compareTo(pointer.getElement()) > 0) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                nodeCounter--;
                return true;

                //Case 2.0: One subtree and its Left
            } else if(pointer.getRight() == null && pointer.getLeft() != null) {
                //System.out.println("one subtree node to be deleted: " + pointer.getElement().toString());

                parent.setLeft(pointer.getLeft());
                nodeCounter--;
                return true;
                //Case 2.1: One subtree and its right
            } else if(pointer.getRight() != null && pointer.getLeft() == null) {
                //System.out.println("One subtree node to be deleted: " + pointer.getElement().toString());

                parent.setRight(pointer.getRight());
                nodeCounter--;
                return true;
            }

            //Case 3: 2 subtrees exist
            if(pointer.getLeft() != null && pointer.getRight() != null) {
                //System.out.println("hello");
                Node<E> temp = pointer;
                temp = temp.getRight();
                while (temp.getLeft() != null) {
                    if(pointer.getLeft().getElement().compareTo(pointer.getElement()) < 0){
                        temp = temp.getLeft();
                    }
                }
                if(temp.getLeft() == null) {
                    minNode = temp;
                }
                pointer.setElement(minNode.getElement());
                pointer.setRight(minNode.getRight());
                nodeCounter--;
                return true;
            }
        }
        return false;
    }



    /**
     * returns an iterator over this collection
     * <p>
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator() {
        AVLIterator iterate = new AVLIterator(root);
        return iterate;
    }

    /**
     * Runs in linear time
     *
     * @return a list of the data in post-order traversal order
     */
    public List<E> getPostOrder() {
        getPostOrder(root);
        return list;
    }

    public Node<E> getPostOrder(Node<E> node) {
        if(node == null) {
            return null;
        } else {
            getPostOrder(node.left);
            getPostOrder(node.right);
            list.add(node.getElement());
        }
        return null;
    }

    /**
     * Runs in linear time
     *
     * @return a list of the data in level-order traversal order
     */
    public List<E> getLevelOrder() {
        List<E> temp = new LinkedList<>();

        visited = new LinkedList<>();

        if(root == null) {
            //System.out.println("Null root");
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
                temp.add(visited.peek().getElement());
                visited.remove();
            }
        }
        return temp;
    }

    /**
     * Runs in linear time
     *
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getPreOrder() {
        getPreOrder(root);
        return list;
    }

    public Node<E> getPreOrder(Node<E> node) {
        if (node == null) {
            return null;
        } else {
            list.add(node.getElement());
            getPreOrder(node.left);
            getPreOrder(node.right);
        }
        return null;
    }
    /**
     * Runs in linear time
     *
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getInOrder(){
        getInOrder(root);
        return list;
    }

    private Node<E> getInOrder(Node<E> node) {

        if(node == null) {
            return null;
        } else {
            getInOrder(node.left);
            list.add(node.getElement());
            getInOrder(node.right);
        }
        return null;
    }

    /**
     * Runs in linear time
     * Removes all the elements from this tree
     */
    public void clear() {
        if(root != null) {
            root.right = null;
            root.left = null;
            nodeCounter = 0;
        }
    }


    //returns height of the root node
    public int height() {
        return height(root);
    }

    /**
     * returns height of an input node
     * @param node node to find height of
     * @return height of a node
     */
    public int height(Node<E> node) {

        if(node == null) {
            return -1;
        }

        int left = height(node.left);
        int right = height(node.right);

        if(left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }


    public Node<E> rotateRight(Node<E> node) {

        Node<E> temp = null;

        if(node.left != null) {
            temp = node.left;
            node.left = temp.right;
            temp.right = node;
            temp.nodeHeight = Math.max(node.nodeHeight, height(temp.left)) + 1;
            node.nodeHeight = Math.max(height(temp.right), height(temp.left)) + 1;
        }
        return temp;

    }

    public Node<E> rotateLeft(Node<E> node) {

        Node<E> temp = null;

        if (node.right != null) {
            temp = node.right;
            node.right = temp.left;
            temp.left = node;
            temp.nodeHeight = Math.max(height(temp.right), height(temp.left)) + 1;
            node.nodeHeight = Math.max(height(temp.right), node.nodeHeight) + 1;
        }
        return temp;
    }

    public int heightDiff(Node<E> node) {
        return height(node.left) - height(node.right);
    }
}