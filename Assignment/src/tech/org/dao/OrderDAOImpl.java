package tech.org.dao;

import tech.org.entity.Order;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean placeOrder(Order order) {
        boolean success = false;
        String sql = "INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, order.getOrderID());
            ps.setInt(2, order.getCustomerID());
            ps.setDate(3, order.getOrderDate());
            ps.setDouble(4, order.getTotalAmount());
            ps.setString(5, order.getStatus());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }

        return success;
    }

    @Override
    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getDate("OrderDate"),
                    rs.getDouble("TotalAmount"),
                    rs.getString("Status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving order: " + e.getMessage());
        }

        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (Connection con = DBConnUtil.getDbConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getDate("OrderDate"),
                    rs.getDouble("TotalAmount"),
                    rs.getString("Status")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String newStatus) {
        boolean success = false;
        String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating order status: " + e.getMessage());
        }

        return success;
    }
}
