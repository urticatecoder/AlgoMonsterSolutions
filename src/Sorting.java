import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sorting {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> scrambled = new ArrayList<>();

        // Generate list of random numbers between 1 and 100, inclusive
        for (int i = 0; i < 100; i++) {
            double x = Math.random();

            if (x > 0.75) {
                list.add(i);
            }
        }

        // Generate scrambled list using the numbers from above
        for (int i = 0; i < list.size(); i++) {
            int x = (int) (Math.random() * list.size());

            scrambled.add(list.remove(x));
        }

        /*System.out.println("Unsorted: " + scrambled);
        quickSort(scrambled, 0, scrambled.size() - 1);
        System.out.println("Sorted: " + scrambled);*/

        System.out.println("Sorted: " + sortList(scrambled));
    }

    public static List<Integer> sortList(List<Integer> unsortedList) {
        return /**CHANGE SORTING ALGORITHM HERE*/ mergeSort(unsortedList, 0, unsortedList.size() - 1);
    }

    public static List<Integer> insertionSort(List<Integer> lst) {
        int lastSortedIndex = 0;

        while (lastSortedIndex < lst.size() - 1) {
            int current = lastSortedIndex + 1;

            while (current > 0 && lst.get(current) < lst.get(current - 1)) {
                int temp = lst.get(current);
                lst.set(current, lst.get(current - 1));
                lst.set(current - 1, temp);
                current--;
            }

            lastSortedIndex++;
        }

        return lst;
    }

    public static List<Integer> selectionSort(List<Integer> lst) {
        int firstUnsortedIndex = 0;

        while (firstUnsortedIndex < lst.size()) {
            int indexOfMinimum = firstUnsortedIndex;
            for (int i = firstUnsortedIndex; i < lst.size(); i++){
                if (lst.get(i) < lst.get(indexOfMinimum)) {
                    indexOfMinimum = i;
                }
            }

            int temp = lst.get(firstUnsortedIndex);
            lst.set(firstUnsortedIndex, lst.get(indexOfMinimum));
            lst.set(indexOfMinimum, temp);

            firstUnsortedIndex++;
        }

        return lst;
    }

    public static List<Integer> bubbleSort(List<Integer> lst) {
        for (int i = 0; i < lst.size(); i++) {
            for (int j = 0; j < lst.size() - i - 1; j++) {
                if (lst.get(j) > lst.get(j + 1)){
                    int temp = lst.get(j);
                    lst.set(j, lst.get(j + 1));
                    lst.set(j + 1, temp);
                }
            }
        }

        return lst;
    }

    public static List<Integer> mergeSort(List<Integer> lst, int left, int right) {
        if (right - left == 0) {
            return lst.subList(left, right + 1);
        }

        return merge(mergeSort(lst, left, (left + right) / 2), mergeSort(lst, (left + right) / 2 + 1, right));
    }

    public static List<Integer> merge(List<Integer> first, List<Integer> second) {
        ArrayList<Integer> merged = new ArrayList<>();

        int left = 0, right = 0;

        /* Note: These if statements could be made a little less convoluted by reordering them; i.e., putting the checks
         * for overflow first.
         */
        while(left < first.size() || right < second.size()) {
            if (left < first.size() && right < second.size() && (first.get(left) <= second.get(right))) {
                merged.add(first.get(left));
                left++;
            } else if (left < first.size() && right < second.size() && (first.get(left) > second.get(right))) {
                merged.add(second.get(right));
                right++;
            } else if (left >= first.size()) {
                merged.add(second.get(right));
                right++;
            } else {
                merged.add(first.get(left));
                left++;
            }
        }

        return merged;
    }


    // Not Working
    public static void quickSort(List<Integer> arr, int start, int end) {
        int pivot = partition(arr, start, end);

        if (start < pivot) {
            quickSort(arr, start, pivot - 1);
        }

        if (end > pivot) {
            quickSort(arr, pivot + 1, end);
        }
    }

    public static int partition(List<Integer> arr, int start, int end) {
        // Choose random element
        int randomIndex = start + (int) (Math.random() * (end - start + 1));
        int pivotValue = arr.get(randomIndex);
        int lastElement = end;

        // Move it to the end
        swap(arr,randomIndex, end);

        // Decrement end pointer so that we don't consider the pivot value itself
        end--;

        while (start < end) {
            // Increment start until we get an element larger than the pivot value
            while (start < arr.size() && arr.get(start) < pivotValue) {
                start++;
            }

            // Decrement end until we get an element smaller than the pivot value
            while (end > 0 && arr.get(end) > pivotValue) {
                end--;
            }

            // Swap the two as long as start is still less than end
            if (start < end) {
                swap(arr, start, end);
                start++;
                end--;
            }
        }

        // Move the pivot value to its correct spot
        swap(arr, start, lastElement);
        return start;
    }

    // Helper for quicksort
    public static void swap(List<Integer> arr, int first, int second) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

}
