import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class BFS {
    public static class Node<T> {
        public T val;
        public Node<T> left;
        public Node<T> right;

        public Node(T val) {
            this(val, null, null);
        }

        public Node(T val, Node<T> left, Node<T> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // this function builds a tree from input; you don't have to modify it
    // learn more about how trees are encoded in https://algo.monster/problems/serializing_tree
    public static <T> Node<T> buildTree(Iterator<String> iter, Function<String, T> f) {
        String val = iter.next();
        if (val.equals("x")) return null;
        Node<T> left = buildTree(iter, f);
        Node<T> right = buildTree(iter, f);
        return new Node<T>(f.apply(val), left, right);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node<Integer> root = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        scanner.close();
        List<List<Integer>> res = levelOrderTraversal(root);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }

    // Given a binary tree, return its level order traversal. The input is the root node of the tree. The output should
    // be a list of lists of integers, with the ith list containing the values of nodes on level i, from left to right.
    public static List<List<Integer>> levelOrderTraversal(Node<Integer> root) {
        Queue<Node<Integer>> q = new LinkedList<>();
        q.add(root);
        ArrayList<List<Integer>> ret = new ArrayList<>();

        while (!q.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                Node<Integer> node = q.poll();
                level.add(node.val);

                if (node.left != null) {
                    q.add(node.left);
                }

                if (node.right != null) {
                    q.add(node.right);
                }
            }

            ret.add(level);
        }

        return ret;
    }

    // Given a tree root, return a list of lists where each list is a level of the tree. The lists should alternate
    // in order: first left to right, then right to left.
    public static List<List<Integer>> zigZagTraversal(Node<Integer> root) {
        ArrayDeque<Node<Integer>> deque = new ArrayDeque<>();
        ArrayList<List<Integer>> ret = new ArrayList<>();
        if (root != null) {
            deque.add(root);
        }
        int level = 0;

        while (!deque.isEmpty()) {
            int dequeSize = deque.size();
            ArrayList<Integer> levelValues = new ArrayList<>();

            for (int i = 0; i < dequeSize; i++) {
                if (level % 2 == 0) {
                    Node<Integer> node = deque.removeFirst();
                    levelValues.add(node.val);
                    if (node.left != null) {
                        deque.addLast(node.left);
                    }
                    if (node.right != null) {
                        deque.addLast(node.right);
                    }

                } else {
                    Node<Integer> node = deque.removeLast();
                    levelValues.add(node.val);
                    if (node.right != null) {
                        deque.addFirst(node.right);
                    }
                    if (node.left != null) {
                        deque.addFirst(node.left);
                    }

                }
            }

            ret.add(levelValues);
            level++;
        }

        return ret;

        /* Alternative solution: Just put the nodes into a queue as we normally do for level order traversal. Then for
        each level, create a new deque, and add to the beginning on odd levels, and add to the end on even levels. Then
        convert that deque to a list and add it to your ret list.
         */
    }

    // Print the view that you would see of the binary tree root when standing on its right side, from top to bottom
    // (i.e. the rightmost node of each level)
    public static List<Integer> binaryTreeRightSideView(Node<Integer> root) {
        Queue<Node<Integer>> q = new ArrayDeque<>();
        if (root != null) {
            q.add(root);
        }

        ArrayList<Integer> arr = new ArrayList<>();

        while (!q.isEmpty()) {
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                Node<Integer> node = q.poll();
                if (i == levelSize - 1) {
                    arr.add(node.val);
                }

                if (node.left != null) {
                    q.add(node.left);
                }

                if (node.right != null) {
                    q.add(node.right);
                }
            }
        }

        return arr;
    }

    // Return the depth of the shallowest leaf node (closest to the root)
    public static int binaryTreeMinDepth(Node<Integer> root) {
        Queue<Node<Integer>> q = new ArrayDeque<>();
        q.add(root);

        int level = 0;

        while (!q.isEmpty()) {
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                Node<Integer> next = q.poll();

                if (next.left == null && next.right == null) {
                    return level;
                }

                if (next.left != null) q.add(next.left);
                if (next.right != null) q.add(next.right);
            }

            level++;
        }

        return 0;
    }
}
