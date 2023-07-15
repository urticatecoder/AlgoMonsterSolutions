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

        System.out.println("Unsorted: " + scrambled);
        System.out.println("Sorted:   " + sortList(scrambled));
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


}
