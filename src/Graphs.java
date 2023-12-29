import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.HashMap;

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

        ArrayList<Integer> row1 = new ArrayList<>();
        ArrayList<Integer> row2 = new ArrayList<>();

        row1.add(1);
        row1.add(0);
        row1.add(2);
        row2.add(4);
        row2.add(5);
        row2.add(3);
        ArrayList<List<Integer>> cell = new ArrayList<>();
        cell.add(row1);
        cell.add(row2);

        System.out.println(cell);

        System.out.println(slidingPuzzleGetNeighbors(cell));
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

    public static int wordLadder(String begin, String end, List<String> wordList) {
        Queue<String> q = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        q.add(begin);
        visited.add(begin);
        int wordCount = 0;

        while (!q.isEmpty()) {
            int levelSize = q.size();


            for (int i = 0; i < levelSize; i++) {
                String currentWord = q.poll();
                List<String> neighbors = getNeighborsWordLadder(wordList, currentWord);

                for (String neighbor : neighbors) {
                    if (neighbor.equals(end)) return wordCount + 1;
                    if (visited.contains(neighbor)) continue;

                    q.add(neighbor);
                    visited.add(neighbor);
                }
            }

            wordCount++;
        }

        return wordCount;
    }


    // Return a list of all words in wordList that differ from word by 1 character
    // Runs in O(nm) time, for n the number of words in wordList, and m the length of the maximum word
    // AlgoMonster's solution maintains a list of unvisited words, and goes through every
    // character in word, changing it to all 26 character and checking to see if the new
    // word is in the set of unvisited ones. It claims this reduces time complexity (of the whole
    // solution) to O(n + m), which I don't believe.
    public static List<String> getNeighborsWordLadder(List<String> wordList, String word) {
        ArrayList<String> neighbors = new ArrayList<>();

        for (String next : wordList) {
            int differCount = 0;
            if (next.equals(word)) continue;
            if (Math.abs(next.length() - word.length()) > 2) continue;

            for (int i = 0; i < word.length(); i++) {
                if (i > next.length() - 1) {
                    differCount++;
                } else if (word.charAt(i) != next.charAt(i)) {
                    differCount++;
                }
            }

            if (differCount <= 1) neighbors.add(next);
        }

        return neighbors;
    }


    // Given a sliding puzzle consisting of six squares (five of them have tiles, represented by nonzero numbers
    // and one of them is an empty space, represented by a zero), return the minimum number of moves it will take
    // to return the puzzle to is original state (i.e. 1 2 3
    //                                                 4 5 0).
    public static int numSteps(List<List<Integer>> initPos) {
        Queue<List<List<Integer>>> q = new ArrayDeque<>();
        HashSet<List<List<Integer>>> visited = new HashSet<>();
        visited.add(initPos);
        q.add(initPos);
        int steps = 0;

        ArrayList<List<Integer>> finalState = new ArrayList<>();
        ArrayList<Integer> row1 = new ArrayList<>();
        ArrayList<Integer> row2 = new ArrayList<>();
        row1.add(1);
        row1.add(2);
        row1.add(3);
        row2.add(4);
        row2.add(5);
        row2.add(0);
        finalState.add(row1);
        finalState.add(row2);


        while (!q.isEmpty()) {
            int n = q.size();

            for (int i = 0; i < n; i++) {
                List<List<Integer>> currentState = q.poll();
                if (currentState.equals(finalState)) return steps;

                for (List<List<Integer>> neighbor : slidingPuzzleGetNeighbors(currentState)) {
                    if (!visited.contains(neighbor)) {
                        q.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            steps++;
        }


        return -1;
    }

    public static List<List<List<Integer>>> slidingPuzzleGetNeighbors(List<List<Integer>> initPos) {
        List<List<List<Integer>>> neighbors = new ArrayList<>();
        int zeroRow = 0;
        int zeroCol = 0;

        for (int i = 0; i < initPos.size(); i++) {
            for (int j = 0; j < initPos.get(0).size(); j++) {
                if (initPos.get(i).get(j) == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        int[] rowOffsets = {0, 1, 0, -1};
        int[] colOffsets = {1, 0, -1, 0};

        for (int i = 0; i < 4; i++) {
            int switchRow = zeroRow + rowOffsets[i];
            int switchCol = zeroCol + colOffsets[i];

            if (switchRow >= 0 && switchRow < 2 && switchCol >= 0 && switchCol < 3) {
                int switchValue = initPos.get(switchRow).get(switchCol);
                ArrayList<List<Integer>> neighbor = new ArrayList<>();
                ArrayList<Integer> firstRow = new ArrayList<>();
                ArrayList<Integer> secondRow = new ArrayList<>();

                for (int j = 0; j < initPos.size(); j++) {
                    for (int k = 0; k < initPos.get(0).size(); k++) {
                        if (j == 0 && j == zeroRow && k == zeroCol) {
                            firstRow.add(switchValue);
                        } else if (j == 0 && j == switchRow && k == switchCol) {
                            firstRow.add(0);
                        } else if (j == 0) {
                            firstRow.add(initPos.get(j).get(k));
                        } else if (j == 1 && j == zeroRow && k == zeroCol) {
                            secondRow.add(switchValue);
                        } else if (j == 1 && j == switchRow && k == switchCol) {
                            secondRow.add(0);
                        } else {
                            secondRow.add(initPos.get(j).get(k));
                        }
                    }
                }

                neighbor.add(firstRow);
                neighbor.add(secondRow);
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    public static List<String> taskScheduling(List<String> tasks, List<List<String>> requirements) {
        // Create graph, get in degree counts
        HashMap<String, List<String>> graph = new HashMap<>();
        HashMap<String, Integer> inCounts = new HashMap<>();

        for (String task : tasks) {
            ArrayList<String> neighbors = new ArrayList<>();
            graph.put(task, neighbors);
            inCounts.put(task, 0);
        }

        for (List<String> dependency : requirements) {
            graph.get(dependency.get(0)).add(dependency.get(1));
            inCounts.put(dependency.get(1), inCounts.get(dependency.get(1)) + 1);
        }

        // Run Kahn's algorithm
        Queue<String> q = new ArrayDeque<>();

        for (String task : inCounts.keySet()) {
            if (inCounts.get(task) == 0) {
                q.add(task);
            }
        }

        ArrayList<String> order = new ArrayList<>();

        while (!q.isEmpty()) {
            String cur = q.poll();
            order.add(cur);
            for (String neighbor : graph.get(cur)) {
                inCounts.put(neighbor, inCounts.get(neighbor) - 1);
                if (inCounts.get(neighbor) == 0) {
                    q.add(neighbor);
                }
            }
        }

        return order;
    }

    public static boolean sequenceReconstruction(List<Integer> original, List<List<Integer>> seqs) {
        // Construct graph
        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        HashMap<Integer, Integer> inDegrees = new HashMap<>();

        for (Integer integer : original) {
            HashSet<Integer> neighbors = new HashSet<>();
            graph.put(integer, neighbors);
            inDegrees.put(integer, 0);
        }

        for (List<Integer> subsequence : seqs) {

            if (subsequence.size() <= 1) continue;

            int left = 0;
            int right = 1;

            while (right < subsequence.size()) {
                if (!graph.get(subsequence.get(left)).contains(subsequence.get(right))) {
                    inDegrees.put(subsequence.get(right), inDegrees.get(subsequence.get(right)) + 1);
                }
                graph.get(subsequence.get(left)).add(subsequence.get(right));

                right++;
                left++;
            }
        }

        // Run Topological sort
        Queue<Integer> q = new ArrayDeque<>();
        for (Integer integer : inDegrees.keySet()) {
            if (inDegrees.get(integer) == 0) {
                q.add(integer);
            }
        }

        while (!q.isEmpty()) {
            if (q.size() > 1) return false;
            int current = q.poll();

            for (int neighbor : graph.get(current)) {
                inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
                if (inDegrees.get(neighbor) == 0) {
                    q.add(neighbor);
                }
            }
        }

        return true;
    }
}
