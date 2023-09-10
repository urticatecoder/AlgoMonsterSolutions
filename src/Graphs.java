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

    // Replace all pixels of the same color that are connected to the given pixel in the given matrix
    public static List<List<Integer>> floodFill(int r, int c, int replacement, List<List<Integer>> image) {
        // DFS solution
        /*HashSet<List<Integer>> visited = new HashSet<>();
        floodFillDfs(r, c, replacement, image, visited);
        return image;*/

        //BFS Solution
        int color = image.get(r).get(c);
        Queue<List<Integer>> q = new ArrayDeque<>();
        HashSet<List<Integer>> visited = new HashSet<>();
        ArrayList<Integer> root = new ArrayList<>();
        root.add(r);
        root.add(c);
        q.add(root);
        visited.add(root);

        while (!q.isEmpty()) {
            List<Integer> current = q.poll();
            image.get(current.get(0)).set(current.get(1), replacement);
            List<List<Integer>> neighbors = getNeighbors(current.get(0), current.get(1), image, color);

            for (List<Integer> neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    q.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return image;
    }

    public static void floodFillDfs(int r, int c, int replacement, List<List<Integer>> image, HashSet<List<Integer>> visited) {
        int color = image.get(r).get(c);
        ArrayList<Integer> pixel = new ArrayList<>();
        pixel.add(r);
        pixel.add(c);
        image.get(r).set(c, replacement);
        visited.add(pixel);

        List<List<Integer>> neighbors = getNeighbors(r, c, image, color);

        for (List<Integer> neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                floodFillDfs(neighbor.get(0), neighbor.get(1), replacement, image, visited);
            }
        }
    }

    public static List<List<Integer>> getNeighbors(int row, int col, List<List<Integer>> image, int color) {
        ArrayList<List<Integer>> neighbors = new ArrayList<>();
        int maxRow = image.size() - 1;
        int maxCol = image.get(0).size() - 1;
        int[] rowDeltas = {-1, 0, 1, 0};
        int[] colDeltas = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> neighbor = new ArrayList<>();
            int newRow = row + rowDeltas[i];
            int newCol = col + colDeltas[i];
            if (newRow >= 0 && newRow <= maxRow && newCol >= 0 && newCol <= maxCol && image.get(newRow).get(newCol) == color) {
                neighbor.add(newRow);
                neighbor.add(newCol);
                neighbors.add(neighbor);
            }
        }

        //System.out.println(neighbors);
        return neighbors;
    }

}
