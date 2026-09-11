package arrays_and_strings.class_problems;

/**
 * Customer identity verification module: produces a reversed copy of a customer
 * name while leaving the original name untouched.
 */
public class ReverseCustomerName {

    public static String reverseCustomerName(String customerName) {
        if (customerName == null) {
            return null;
        }
        // toCharArray() hands back a copy, so the caller's original String is never modified.
        char[] characters = customerName.toCharArray();
        int left = 0;
        int right = characters.length - 1;
        while (left < right) {
            char swapped = characters[left];
            characters[left] = characters[right];
            characters[right] = swapped;
            left++;
            right--;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        String[] customerNames = { "Sunil", "Muhammad Farid", "A", "" };
        for (String customerName : customerNames) {
            System.out.println("Original Name: " + customerName);
            System.out.println("Reversed Name: " + reverseCustomerName(customerName));
            System.out.println();
        }
    }
}
