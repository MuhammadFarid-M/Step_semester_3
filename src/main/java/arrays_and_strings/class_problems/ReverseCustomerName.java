package arrays_and_strings.class_problems;

public class ReverseCustomerName {

    public static String reverseCustomerName(String customerName) {
        if (customerName == null) {
            return null;
        }

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
