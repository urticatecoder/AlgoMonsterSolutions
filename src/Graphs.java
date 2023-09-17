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
//        Scanner scanner = new Scanner(System.in);
//        int graphLength = Integer.parseInt(scanner.nextLine());
//        List<List<Integer>> graph = new ArrayList<>();
//        for (int i = 0; i < graphLength; i++) {
//            graph.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
//        }
//        int a = Integer.parseInt(scanner.nextLine());
//        int b = Integer.parseInt(scanner.nextLine());
//        scanner.close();
//        int res = shortestPath(graph, a, b);
//        System.out.println(res);

        System.out.println(getNeighbors("1459"));
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

    // Get the number of connected islands of value 1 from grid
    // Note that you could also keep track of visited nodes here by setting their values to 0 or something else in the
    // grid
    public static int countNumberOfIslands(List<List<Integer>> grid) {
        HashSet<List<Integer>> visited = new HashSet<>();
        int islandCount = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                ArrayList<Integer> pair = new ArrayList<>();
                pair.add(i);
                pair.add(j);

                if (grid.get(i).get(j) == 1 && !visited.contains(pair)) {
                    islandCount++;
                    islandCountBfs(pair, grid, visited);
                }
            }
        }

        return islandCount;
    }

    public static void islandCountBfs(ArrayList<Integer> pair, List<List<Integer>> grid, HashSet<List<Integer>> visited) {
        Queue<List<Integer>> q = new ArrayDeque<>();
        q.add(pair);
        visited.add(pair);

        while (!q.isEmpty()) {
            List<Integer> next = q.poll();
            List<List<Integer>> neighbors = getNeighbors(next.get(0), next.get(1), grid);

            for (List<Integer> neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    q.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    public static List<List<Integer>> getNeighbors (int row, int col, List<List<Integer>> grid) {
        int[] deltaRow = {-1, 0, 1, 0};
        int[] deltaCol = {0, 1, 0, -1};
        int maxRow = grid.size() - 1;
        int maxCol = grid.get(0).size() - 1;
        ArrayList<List<Integer>> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int newRow = row + deltaRow[i];
            int newCol = col + deltaCol[i];

            if (newRow >= 0 && newRow <= maxRow && newCol >= 0 && newCol <= maxCol && grid.get(newRow).get(newCol) == 1) {
                ArrayList<Integer> neighbor = new ArrayList<>();
                neighbor.add(newRow);
                neighbor.add(newCol);
                neighbors.add(neighbor);
            }
        }

        //System.out.println(neighbors);
        return neighbors;
    }

    // Return the minimum number of moves it would take a knight (in chess) to get to the coordinates (x, y) starting
    // at (0, 0) on an infinite chess board.
    public static int getKnightShortestPath(int x, int y) {
        // Represent coordinates as an array list with two integers
        Queue<List<Integer>> q = new ArrayDeque<>();
        HashSet<List<Integer>> visited = new HashSet<>();
        ArrayList<Integer> root = new ArrayList<>();
        root.add(0);
        root.add(0);
        q.add(root);
        visited.add(root);

        int level = 0;

        while (!q.isEmpty()) {
            int n = q.size();

            for (int i = 0; i < n; i++) {
                List<Integer> coordinate = q.poll();
                List<List<Integer>> neighbors = getNeighbors(coordinate);

                for (List<Integer> neighbor : neighbors) {
                    if (neighbor.get(0) == x && neighbor.get(1) == y) {
                        return level + 1;
                    }

                    if (!visited.contains(neighbor)) {
                        q.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            level++;
        }

        return level;
    }

    public static List<List<Integer>> getNeighbors(List<Integer> coordinate) {
        int[] deltaRow = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] deltaCol = {1, 2, 2, 1, -1, -2, -2, -1};
        ArrayList<List<Integer>> neighbors = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ArrayList<Integer> neighbor = new ArrayList<>();
            neighbor.add(coordinate.get(0) + deltaRow[i]);
            neighbor.add(coordinate.get(1) + deltaCol[i]);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    public static List<List<Integer>> mapGateDistances(List<List<Integer>> dungeonMap) {
        Queue<List<Integer>> q = new ArrayDeque<>();
        HashSet<List<Integer>> visited = new HashSet<>();
        int level = 0;

        for (int i = 0; i < dungeonMap.size(); i++) {
            for (int j = 0; j < dungeonMap.get(0).size(); j++) {
                if (dungeonMap.get(i).get(j) == 0) {
                    ArrayList<Integer> coordinates = new ArrayList<>();
                    coordinates.add(i);
                    coordinates.add(j);
                    q.add(coordinates);
                    visited.add(coordinates);
                }
            }
        }

        while (!q.isEmpty()) {
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                List<Integer> current = q.poll();
                List<List<Integer>> neighbors = getNeighbors(current, dungeonMap);

                for (List<Integer> neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        // Set the value at the coordinates of neighbor
                        dungeonMap.get(neighbor.get(0)).set(neighbor.get(1), level + 1);
                        q.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            level++;
        }

        return dungeonMap;
    }

    public static List<List<Integer>> getNeighbors(List<Integer> cell, List<List<Integer>> dungeonMap) {
        int[] rowDelta = {-1, 0, 1, 0};
        int[] colDelta = {0, 1, 0, -1};
        int maxRow = dungeonMap.size() - 1;
        int maxCol = dungeonMap.get(0).size() - 1;
        ArrayList<List<Integer>> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int newRow = cell.get(0) + rowDelta[i];
            int newCol = cell.get(1) + colDelta[i];

            if (newRow >= 0 && newRow <= maxRow && newCol >= 0 && newCol <= maxCol && dungeonMap.get(newRow).get(newCol) == 2147483647) {
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(newRow);
                coordinates.add(newCol);
                neighbors.add(coordinates);
            }
        }

        return neighbors;
    }

    public static List<String> getNeighbors(String combo) {
        int combination = Integer.parseInt(combo);
        int[] placeValues = {1, 10, 100, 1000};
        ArrayList<String> ret = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int intermediary = combination / placeValues[i];

            if (intermediary % 10 == 9) {
                ret.add(Integer.toString(combination - 9 * placeValues[i]));
                ret.add(Integer.toString(combination - placeValues[i]));
            } else if (intermediary % 10 == 0) {
                ret.add(Integer.toString(combination + placeValues[i]));
                ret.add(Integer.toString(combination + 9 * placeValues[i]));
            } else {
                ret.add(Integer.toString(combination + placeValues[i]));
                ret.add(Integer.toString(combination - placeValues[i]));
            }

        }

        return ret;
    }

    // Return the minimum number of steps that it takes to get from "0000" to targetCombo
    public static int numSteps(String targetCombo, List<String> trappedCombos) {
        Queue<String> q = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        HashSet<String> trapped = new HashSet<>(trappedCombos);
        int level = 0;
        q.add("0000");
        visited.add("0000");

        while(!q.isEmpty()) {
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                String currentCombo = q.poll();

                for (String neighbor : getNeighborsCombo(currentCombo)) {
                    if (!visited.contains(neighbor) && !trapped.contains(neighbor)) {
                        if (neighbor.equals(targetCombo)) {
                            return level + 1;
                        }

                        q.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            level++;
        }

        return -1;
    }

    public static List<String> getNeighborsCombo(String combo) {
        int combination = Integer.parseInt(combo);
        int[] placeValues = {1, 10, 100, 1000};
        ArrayList<String> ret = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int intermediary = combination / placeValues[i];
            String str1 = "";
            String str2 = "";
            if (intermediary % 10 == 9) {
                str1 = Integer.toString(combination - 9 * placeValues[i]);
                str2 = Integer.toString(combination - placeValues[i]);
            } else if (intermediary % 10 == 0) {
                str1 = Integer.toString(combination + placeValues[i]);
                str2 = Integer.toString(combination + 9 * placeValues[i]);
            } else {
                str1 = Integer.toString(combination + placeValues[i]);
                str2 = Integer.toString(combination - placeValues[i]);
            }

            while (str1.length() < 4) {
                str1 = "0" + str1;
            }

            while (str2.length() < 4) {
                str2 = "0" + str2;
            }

            ret.add(str1);
            ret.add(str2);
        }

        return ret;
    }
}
