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

        System.out.println();
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

    // Generate a list of all valid combinations of n parentheses
    public static List<String> generateParentheses(int n) {
        ArrayList<String> paths = new ArrayList<String>();
        StringBuilder parentheses = new StringBuilder();
        generate(parentheses, paths, n, 0);
        return paths;
    }

    public static void generate(StringBuilder parentheses, ArrayList<String> paths, int n, int openParentheses) {
        if (openParentheses == 0 && n == 0) {
            paths.add(parentheses.toString());
            return;
        }

        String[] options = {"(", ")"};

        for (String parenthesis : options) {
            if (parenthesis.equals("(") && n == 0) {
                continue;
            } else if (parenthesis.equals(")") && openParentheses == 0) {
                continue;
            } else if (parenthesis.equals("(")) {
                parentheses.append(parenthesis);
                generate(parentheses, paths, n - 1, openParentheses + 1);
                parentheses.deleteCharAt(parentheses.length() - 1);
            } else if (parenthesis.equals(")")) {
                parentheses.append(parenthesis);
                generate(parentheses, paths, n, openParentheses - 1);
                parentheses.deleteCharAt(parentheses.length() - 1);
            }
        }
    }

    // Return a list of every permutation of the letters contained in the string
    public static List<String> permutations(String letters) {
        StringBuilder initial = new StringBuilder();
        ArrayList<String> paths = new ArrayList<>();
        String[] lettersArray = letters.split("");
        boolean[] used = new boolean[lettersArray.length];


        permutationsDfs(0, initial, paths, lettersArray, used);

        return paths;
    }

    public static void permutationsDfs(int startIndex, StringBuilder current, List<String> paths, String[] letters, boolean[] used) {
        if (startIndex == used.length) {
            paths.add(current.toString());
            return;
        }

        for (int i = 0; i < letters.length; i++) {
            if (!used[i]) {
                current.append(letters[i]);
                used[i] = true;
                permutationsDfs(startIndex + 1, current, paths, letters, used);
                used[i] = false;
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    // Return the number of ways to decode digits where each alphabet letter is mapped to a number: a -> 1, b -> 2, etc.
    // NOTE: This could be faster if, instead of creating a substring and passing it, I just passed an int that keeps
    // track of what index to start at. Making a substring is O(n), whereas checking at an index is O(1).
    public static int decodeWays(String digits) {
        HashMap<String, Integer> memo = new HashMap<>();

        return decodeDfs(digits, memo);
    }

    public static int decodeDfs(String digits, HashMap<String, Integer> memo) {
        if (digits.length() == 0) return 1;

        if (memo.containsKey(digits)) return memo.get(digits);

        int ways = 0;
        if (digits.charAt(0) == '0') return ways;

        ways += decodeDfs(digits.substring(1), memo);

        if ((digits.length() > 1 && Integer.parseInt(digits.substring(0, 1)) == 1) || (digits.length() > 1 && Integer.parseInt(digits.substring(0, 1)) == 2 && Integer.parseInt(digits.substring(1, 2)) <= 6)) {
            ways += decodeDfs(digits.substring(2), memo);
        }

        memo.put(digits, ways);

        return ways;
    }
}
