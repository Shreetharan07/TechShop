package tech.org.dao;

import tech.org.entity.Customer;
import java.util.List;

public interface CustomerDAO {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
}
