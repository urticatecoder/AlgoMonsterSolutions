import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Backtracking {
    public static class Node<T> {
        public T val;
        public List<Node<T>> children;

        public Node(T val) {
            this(val, new ArrayList<>());
        }

        public Node(T val, List<Node<T>> children) {
            this.val = val;
            this.children = children;
        }
    }

    public static <T> Node<T> buildTree(Iterator<String> iter, Function<String, T> f) {
        String val = iter.next();
        int num = Integer.parseInt(iter.next());
        ArrayList<Node<T>> children = new ArrayList<>();
        for (int i = 0; i < num; i++)
            children.add(buildTree(iter, f));
        return new Node<T>(f.apply(val), children);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Node<Integer> root = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
//        scanner.close();
//        List<String> res = ternaryTreePaths(root);
//        for (String line : res) {
//            System.out.println(line);
//        }


    }

    // Return a list that contains all root to leaf paths in the ternary tree
    public static List<String> ternaryTreePaths(Node<Integer> root) {
        ArrayList<String> current = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        ternaryPathsHelper(root, current, paths);

        return paths;
    }

    public static void ternaryPathsHelper(Node<Integer> root, List<String> current, List<String> paths) {
        if (root.children.size() == 0) {
            current.add(Integer.toString(root.val));
            String path = String.join("->", current);
            paths.add(path);
            current.remove(current.size() - 1);
            return;
        }

        for (Node<Integer> child : root.children) {
            current.add(Integer.toString(root.val));
            ternaryPathsHelper(child, current, paths);
            current.remove(current.size() - 1);
        }
    }

    // Find all n letter words that are composed of "a" and "b"
    public static List<String> letterCombination(int n) {
        ArrayList<String> current = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        letterCombinationHelper(n, current, paths);

        return paths;
    }

    public static void letterCombinationHelper(int n, ArrayList<String> current, ArrayList<String> paths) {
        if (n == 0) {
            String path = String.join("", current);
            paths.add(path);
            return;
        }

        final String[] letters = {"a", "b"};

        for (String letter : letters) {
            current.add(letter);
            letterCombinationHelper(n - 1, current, paths);
            current.remove(current.size() - 1);
        }
    }

    // Get all possible phone numbers
    public static List<String> letterCombinationsOfPhoneNumber(String digits) {
        Map<Character, char[]> digitsToLetters = Map.of(
                '2', "abc".toCharArray(),
                '3', "def".toCharArray(),
                '4', "ghi".toCharArray(),
                '5', "jkl".toCharArray(),
                '6', "mno".toCharArray(),
                '7', "pqrs".toCharArray(),
                '8', "tuv".toCharArray(),
                '9', "wxyz".toCharArray()
        );

        StringBuilder curPath = new StringBuilder();
        ArrayList<String> paths = new ArrayList<>();
        phoneNumberHelper(digits, 0, curPath, paths, digitsToLetters);
        return paths;
    }

    public static void phoneNumberHelper(String digits, int current, StringBuilder curPath, ArrayList<String> paths, Map<Character, char[]> digitsMap) {
        if (current == digits.length()) {
            paths.add(curPath.toString());
            return;
        }

        char[] possibleLetters = digitsMap.get(digits.charAt(current));

        for (char letter : possibleLetters) {
            curPath.append(letter);
            phoneNumberHelper(digits, current + 1, curPath, paths, digitsMap);
            curPath.deleteCharAt(curPath.length() - 1);
        }
    }

    // Get all possible combinations of palindromes from the string s
    public static List<List<String>> partition(String s) {
        ArrayList<String> current = new ArrayList<>();
        ArrayList<List<String>> paths = new ArrayList<>();
        partitionDfs(current, paths, s);

        return paths;
    }

    public static void partitionDfs(ArrayList<String> current, List<List<String>> paths, String s) {
        if (s.length() == 0) {
            ArrayList<String> path = new ArrayList<>(current);
            paths.add(path);
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            String prefix = s.substring(0, i + 1);
            if (!isPalindrome(prefix)) {
                continue;
            }
            current.add(prefix);
            partitionDfs(current, paths, s.substring(i + 1));
            current.remove(current.size() - 1);
        }
    }

    public static boolean isPalindrome(String s) {
        int start = 0, end = s.length() - 1;

        while (start <= s.length() - 1) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }

            start++;
            end--;
        }

        return true;
    }
}
