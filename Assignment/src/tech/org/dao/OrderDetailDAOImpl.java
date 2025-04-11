package tech.org.dao;

import tech.org.entity.OrderDetail;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean addOrderDetail(OrderDetail detail) {
        boolean success = false;
        String sql = "INSERT INTO OrderDetails (OrderDetailID, OrderID, ProductID, Quantity, Discount) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detail.getOrderDetailID());
            ps.setInt(2, detail.getOrderID());
            ps.setInt(3, detail.getProductID());
            ps.setInt(4, detail.getQuantity());
            ps.setDouble(5, detail.getDiscount());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding order detail: " + e.getMessage());
        }

        return success;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail(
                    rs.getInt("OrderDetailID"),
                    rs.getInt("OrderID"),
                    rs.getInt("ProductID"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Discount")
                );
                details.add(detail);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching order details: " + e.getMessage());
        }

        return details;
    }
}
