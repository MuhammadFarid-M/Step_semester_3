package data_structures.class_problems;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Problem 2: Customer Registry.
 *
 * The same real-world customer may be added from different branches. Equality
 * is defined on ID and name so those duplicates collapse, and hashCode is
 * redefined alongside it so the HashSet actually finds them - overriding
 * equals without hashCode would let two equal customers land in different
 * buckets and both be stored.
 */
public class CustomerRegistry {

    static final class Customer {

        private final int id;
        private final String name;

        Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Customer)) {
                return false;
            }
            Customer that = (Customer) other;
            return id == that.id && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            // Must agree with equals: equal customers must hash equally.
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "(" + id + ", " + name + ")";
        }
    }

    private final Set<Customer> customers = new HashSet<>();

    /** Returns true when the customer was new, false when it was a duplicate. */
    boolean add(Customer customer) {
        return customers.add(customer);
    }

    boolean contains(Customer customer) {
        return customers.contains(customer);
    }

    int uniqueCount() {
        return customers.size();
    }

    public static void main(String[] args) {
        CustomerRegistry registry = new CustomerRegistry();

        System.out.println(registry.add(new Customer(101, "Asha")));
        boolean secondAdd = registry.add(new Customer(101, "Asha"));
        System.out.println(secondAdd + (secondAdd ? "" : " (duplicate rejected)"));
        System.out.println(registry.add(new Customer(102, "Ravi")));

        System.out.println("unique count " + registry.uniqueCount());
        System.out.println("contains: " + registry.contains(new Customer(101, "Asha")));
    }
}
