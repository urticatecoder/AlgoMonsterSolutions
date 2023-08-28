import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

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
}
