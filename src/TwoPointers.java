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

    // Return a list of all the indices in original for which the next check.length() character form an anagram
    // of check.
    public static List<Integer> findAllAnagrams(String original, String check) {
        if (original.length() < check.length()) {
            return List.of();
        }

        HashMap<Character, Integer> checkCharacterCounts = new HashMap<>();
        for (int i = 0; i < check.length(); i++) {
            checkCharacterCounts.putIfAbsent(check.charAt(i), 0);
            checkCharacterCounts.put(check.charAt(i), checkCharacterCounts.get(check.charAt(i)) + 1);
        }

        HashMap<Character, Integer> originalCharacterCounts = new HashMap<>();
        for (int i = 0; i < check.length(); i++) {
            originalCharacterCounts.putIfAbsent(original.charAt(i), 0);
            originalCharacterCounts.put(original.charAt(i), originalCharacterCounts.get(original.charAt(i)) + 1);
        }

        ArrayList<Integer> indices = new ArrayList<>();
        int left = 0;
        int right = check.length() - 1;

        while (right < original.length()) {
            if (checkCharacterCounts.equals(originalCharacterCounts)) indices.add(left);
            if (right == original.length() - 1) break;

            if (originalCharacterCounts.get(original.charAt(left)) == 1) {
                originalCharacterCounts.remove(original.charAt(left));
            } else {
                originalCharacterCounts.put(original.charAt(left), originalCharacterCounts.get(original.charAt(left)) - 1);
            }
            left++;
            right++;
            originalCharacterCounts.putIfAbsent(original.charAt(right), 0);
            originalCharacterCounts.put(original.charAt(right), originalCharacterCounts.get(original.charAt(right)) + 1);
        }

        return indices;
    }

    // Return the length of the longest contiguous subarray of nums whose sum is less than target.
    public static int subarraySumLongest(List<Integer> nums, int target) {
        int longest = 0;
        int left = 0, right = 0;
        int curSum = nums.get(left);

        while (right < nums.size()) {
            if (curSum > target) {
                if (left == right) {
                    left++;
                    right++;
                    curSum = nums.get(left);
                } else {
                    curSum -= nums.get(left);
                    left++;
                }
            } else {
                if (right - left > longest) {
                    longest = right - left + 1;
                }
                right++;

                if (right != nums.size()) {
                    curSum += nums.get(right);
                }
            }
        }

        return longest;
    }
}
