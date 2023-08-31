import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.HashSet;

public class Graphs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int graphLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < graphLength; i++) {
            graph.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = shortestPath(graph, a, b);
        System.out.println(res);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    // Return the length of the shortest path from a to b
    public static int shortestPath(List<List<Integer>> graph, int a, int b) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(a);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(a);
        int level = 0;

        while (!q.isEmpty()) {
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                int current = q.poll();

                for (Integer connection : graph.get(current)) {
                    // You could also put this outside of the loop, and remove the +1.
                    // You probably should do that.
                    if (connection == b) return level + 1;
                    if (visited.contains(connection)) {
                        continue;
                    }

                    q.add(connection);
                    visited.add(connection);
                }
            }
            level++;

        }

        return level;
    }
}
