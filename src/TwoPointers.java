import java.util.*;

public class TwoPointers {
    public static class Node<T> {
        public T val;
        public Node<T> next;

        public Node(T val) {
            this(val, null);
        }

        public Node(T val, Node<T> next) {
            this.val = val;
            this.next = next;
        }
    }

    // Modify (the sorted) arr in place so that the first elements are all the unique elements of arr. Return the
    // number of non duplicate elements
    public static int removeDuplicates(List<Integer> arr) {
        int fast = 0;
        int slow = 0;


        while (fast < arr.size()) {
            if (arr.get(fast) != arr.get(slow)) {
                slow++;
                arr.set(slow, arr.get(fast));
                fast++;
            } else {
                fast++;
            }
        }

        // return slow + 1 because slow is pointing to the INDEX of the last unique element, and arrays are 0 indexed.
        return slow + 1;
    }

    // Return the middle node of a linked list
    public static int middleOfLinkedList(Node<Integer> head) {
        if (head == null) {
            return 0;
        }

        Node<Integer> slow = head;
        Node<Integer> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.val;
    }

    // Move all nonzero elements to the beginning of the array while maintaining their order, and shift
    // all zeros to the end
    public static void moveZeros(List<Integer> nums) {
        int slow = 0;
        int fast = 0;

        while (fast < nums.size()) {
            if (nums.get(fast) != 0) {
                int temp = nums.get(fast);
                nums.set(fast, nums.get(slow));
                nums.set(slow, temp);
                slow++;
            }

            fast++;
        }
    }
}
