package tech.org.dao;

import tech.org.entity.Customer;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean addCustomer(Customer customer) {
        boolean success = false;
        String sql = "INSERT INTO Customers (CustomerID, FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhone());
            ps.setString(6, customer.getAddress());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }

        return success;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Address")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }

        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try (Connection con = DBConnUtil.getDbConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Address")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }

        return customers;
    }
}
