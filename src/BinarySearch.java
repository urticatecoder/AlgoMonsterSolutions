import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        System.out.println(squareRoot(104));
    }

    public static int vanillaBinarySearch(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;

        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            if (arr.get(midpoint) == target) {
                return midpoint;
            } else if (arr.get(midpoint) < target) {
                left = midpoint + 1;
            } else {
                right = midpoint - 1;
            }
        }

        return -1;
    }

    // Find the first true in a sorted boolean array
    public static int findBoundary(List<Boolean> arr) {
        /*int left = 0, right = arr.size() - 1;

        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            if (!arr.get(midpoint)) {
                left = midpoint + 1;
            } else if (midpoint == 0) {
                return 0;
            } else if (!arr.get(midpoint - 1)) {
                return midpoint;
            } else {
                right = midpoint - 1;
            }
        }

        return -1;*/
        ///////////////////////////////////////////////////////////////////
        int left = 0, right = arr.size() - 1;

        int currentTrue = -1;

        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            if (!arr.get(midpoint)) {
                left = midpoint + 1;
            } else {
                currentTrue = midpoint;
                right = midpoint - 1;
            }
        }

        return currentTrue;
        ///////////////////////////////////////////////////////////////////
        /*int left = 0, right = arr.size() - 1;

        while (left < right) {
            int midpoint = left + (right - left) / 2;

            if (!arr.get(midpoint)) {
                left = midpoint + 1;
            } else {
                right = midpoint;
            }
        }

        return arr.get(left) ? left : -1;*/
    }

    // Find the first element in the sorted array that is greater than or equal to target
    public static int firstNotSmaller(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;
        int current = 0;

        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            if (arr.get(midpoint) < target) {
                left = midpoint + 1;
            } else {
                current = midpoint;
                right = midpoint - 1;
            }
        }

        return current;
    }

    // Find the first occurrence of target in the array and return its index
    public static int findFirstOccurrence(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;

        int current = -1;
        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            if (arr.get(midpoint) == target) {
                current = midpoint;
                right = midpoint - 1;
            } else if (arr.get(midpoint) < target) {
                left = midpoint + 1;
            } else {
                right = midpoint - 1;
            }
        }

        return current;
    }

    // This is not from AlgoMonster - as my own experimentation, I wanted to make a square root algorithm that
    // calculated the square root of x up to a given tolerance
    public static double sqrt(double x, double tolerance) {
        double left = 0, right = x;
        double squareRoot = x / 2;

        while (Math.abs(x - (squareRoot * squareRoot)) > tolerance * tolerance) {
            squareRoot = (left + right) / 2;

            if (squareRoot * squareRoot == x) {
                return squareRoot;
            } else if (squareRoot * squareRoot > x) {
                right = squareRoot;
            } else {
                left = squareRoot;
            }
        }

        return squareRoot;
    }

    // Find square root of n, with decimals truncated
    public static int squareRoot(int n) {
        int left = 0, right = n;

        int lastLarger = 0;
        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            if (midpoint * midpoint == n) {
                return midpoint;
            } else if (midpoint * midpoint > n) {
                lastLarger = midpoint;
                right = midpoint - 1;
            } else {
                left = midpoint + 1;
            }
        }

        return lastLarger - 1;
    }

    // Return the minimum possible amount of time that it would take numCoworkers workers to read through the newspapers
    // contained in newspapersReadTimes if you divide the load optimally
    public static int newspapersSplit(List<Integer> newspapersReadTimes, int numCoworkers) {
        int left = max(newspapersReadTimes);
        int right = sum(newspapersReadTimes);

        int current = left + (right - left) / 2;
        while (left <= right) {
            int midpoint = left + (right - left) / 2;

            int hours = 0;
            int coworkers = 0;

            for (int readTime : newspapersReadTimes) {
                if (hours + readTime > midpoint) {
                    coworkers++;
                    hours = 0;
                }

                hours += readTime;
            }

            if (hours != 0) {
                coworkers++;
            }

            if (coworkers <= numCoworkers) {
                current = midpoint;
                right = midpoint - 1;
            } else {
                left = midpoint + 1;
            }
        }

        return current;
    }

    // Helper for newspapersSplit
    public static int max(List<Integer> newspapersReadTimes) {
        int max = newspapersReadTimes.get(0);

        for (int readTime : newspapersReadTimes) {
            if (readTime > max) {
                max = readTime;
            }
        }

        return max;
    }

    // Helper for newspapersSplit
    public static int sum(List<Integer> newspapersReadTimes) {
        int sum = 0;

        for (int readTime : newspapersReadTimes) {
            sum += readTime;
        }

        return sum;
    }
}
