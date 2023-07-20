import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class DFS {
    public static int index = 0;
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node<Integer> root = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        scanner.close();

        System.out.println(serialize(deserialize("5 4 3 x x 8 x x 6 x x")));
    }

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

    public static void preOrderTraversal(Node tree) {
        if (tree != null) {
            System.out.println(tree.val);
            preOrderTraversal(tree.left);
            preOrderTraversal(tree.right);
        }
    }

    // Get maximum depth of a tree (depth is the number edges from the root node to a node)
    public static int treeMaxDepth(Node<Integer> root) {
        // null and leaf nodes both have depth 0
        // We could also return 0 just for root == null, which would be counting the number of nodes.
        // then, the max depth would be the return value - 1.
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }


        int maxDepthLeft = 1 + treeMaxDepth(root.left);
        int maxDepthRight = 1 + treeMaxDepth(root.right);


        return Math.max(maxDepthLeft, maxDepthRight);
    }


    public static int visibleTreeNode(Node<Integer> root) {
        return dfsVisibleTreeNode(root, root.val);
    }

    public static int dfsVisibleTreeNode(Node<Integer> root, int currentLargest) {
        if (root == null) {
            return 0;
        } else if (root.val >= currentLargest) {
            return 1 + dfsVisibleTreeNode(root.left, root.val) + dfsVisibleTreeNode(root.right, root.val);
        } else {
            return dfsVisibleTreeNode(root.left, currentLargest) + dfsVisibleTreeNode(root.right, currentLargest);
        }
    }

    // Return whether or not the tree is balanced - a balanced tree has subtrees
    // whose heights differ by no more than 1
    public static boolean isBalanced(Node<Integer> tree) {
        return getHeight(tree) == -1 ? false : true;
    }

    public static int getHeight(Node<Integer> tree) {
        if (tree == null) return 0;

        int left = 1 + getHeight(tree.left);
        int right = 1 + getHeight(tree.right);

        if(left == 0 || right == 0) {
            return -1;
        }
        if (Math.abs(left - right) > 1){
            return -1;
        } else {
            return Math.max(left, right);
        }
    }

    // Returns the string version of the input tree, using prefix order
    public static String serialize(Node root) {
        if (root == null) {
            return "x";
        }
        return root.val + " " + serialize(root.left) + " " + serialize(root.right);
    }

    // Returns the tree represented by the given string, using prefix order
    // You can also do this by passing a list and keeping a global index variable, but
    // an iterator is a little more elegant
    public static Node deserialize(String root) {
        List<String> tree = Arrays.asList(root.split(" "));
        Iterator<String> it = tree.iterator();

        return makeTree(it);
    }

    public static Node makeTree(Iterator<String> it) {
        // You might not need this because a valid input string should always end with "x" (i think)
        if (!it.hasNext()) return null;

        String value = it.next();
        if (value.equals("x")) return null;

        Node root = new Node(Integer.parseInt(value));

        root.left = makeTree(it);
        root.right = makeTree(it);

        return root;
    }
}
