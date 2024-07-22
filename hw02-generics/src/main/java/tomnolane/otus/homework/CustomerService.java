package tomnolane.otus.homework;

import java.util.*;

public class CustomerService {
    private final TreeMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customers.firstEntry();
        if (entry != null) {
            return new AbstractMap.SimpleEntry<>(copyCustomer(entry.getKey()), entry.getValue());
        }
        return null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customers.higherEntry(customer);
        if (entry != null) {
            return new AbstractMap.SimpleEntry<>(copyCustomer(entry.getKey()), entry.getValue());
        }
        return null;
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Customer copyCustomer(Customer customer) {
        return new Customer(customer.getId(), customer.getName(), customer.getScores());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerService that = (CustomerService) o;
        return Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customers);
    }
}

