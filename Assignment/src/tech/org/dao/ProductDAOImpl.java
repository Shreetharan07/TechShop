package tech.org.dao;

import tech.org.entity.Product;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean addProduct(Product product) {
        boolean success = false;
        String sql = "INSERT INTO Products (ProductID, ProductName, Description, Price) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, product.getProductID());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }

        return success;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT * FROM Products WHERE ProductID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving product: " + e.getMessage());
        }

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection con = DBConnUtil.getDbConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }

        return products;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean success = false;
        String sql = "UPDATE Products SET ProductName = ?, Description = ?, Price = ? WHERE ProductID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getProductID());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }

        return success;
    }
}
