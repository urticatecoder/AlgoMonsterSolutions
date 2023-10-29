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

    // Return a list that contains the indices of the two elements that sum to target in O(n) time without
    // using any auxiliary space.
    public static List<Integer> twoSumSorted(List<Integer> arr, int target) {
        int left = 0;
        int right = arr.size() - 1;
        ArrayList<Integer> ret = new ArrayList<>();

        while (left < right) {
            if (arr.get(right) > target - arr.get(left)) {
                right--;
            } else if (arr.get(right) < target - arr.get(left)) {
                left++;
            } else if (arr.get(right) == target - arr.get(left)) {
                ret.add(left);
                ret.add(right);
                return ret;
            }
        }

        return ret;
    }

    // Return whether or not the string s is a palindrome.
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    // Return the maximum sum of all contiguous subarrays of length k
    public static int subarraySumFixed(List<Integer> nums, int k) {
        int left = 0;
        int right = left + k - 1;
        int curSum = 0;
        int maxSum = 0;
        for (int i = 0; i <= right; i++) {
            curSum += nums.get(i);
        }

        while (right < nums.size() - 1) {
            curSum -= nums.get(left);
            left++;
            right++;
            curSum += nums.get(right);
            if (curSum > maxSum) {
                maxSum = curSum;
            }
        }

        return maxSum;
    }
}
