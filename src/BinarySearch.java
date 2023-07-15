import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {

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
}
