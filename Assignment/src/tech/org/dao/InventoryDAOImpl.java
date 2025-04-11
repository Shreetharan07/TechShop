package tech.org.dao;

import tech.org.entity.Inventory;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    @Override
    public boolean addInventoryItem(Inventory inventory) {
        boolean success = false;
        String sql = "INSERT INTO Inventory (InventoryID, ProductID, QuantityInStock, LastStockUpdate) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, inventory.getInventoryID());
            ps.setInt(2, inventory.getProductID());
            ps.setInt(3, inventory.getQuantityInStock());
            ps.setDate(4, inventory.getLastStockUpdate());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding inventory item: " + e.getMessage());
        }

        return success;
    }

    @Override
    public Inventory getInventoryByProductId(int productId) {
        Inventory inventory = null;
        String sql = "SELECT * FROM Inventory WHERE ProductID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                inventory = new Inventory(
                    rs.getInt("InventoryID"),
                    rs.getInt("ProductID"),
                    rs.getInt("QuantityInStock"),
                    rs.getDate("LastStockUpdate")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving inventory: " + e.getMessage());
        }

        return inventory;
    }

    @Override
    public boolean updateStock(int productId, int newQuantity) {
        boolean success = false;
        String sql = "UPDATE Inventory SET QuantityInStock = ?, LastStockUpdate = CURRENT_DATE WHERE ProductID = ?";

        try (Connection con = DBConnUtil.getDbConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating inventory stock: " + e.getMessage());
        }

        return success;
    }

    @Override
    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";

        try (Connection con = DBConnUtil.getDbConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inventory inventory = new Inventory(
                    rs.getInt("InventoryID"),
                    rs.getInt("ProductID"),
                    rs.getInt("QuantityInStock"),
                    rs.getDate("LastStockUpdate")
                );
                inventoryList.add(inventory);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching inventory list: " + e.getMessage());
        }

        return inventoryList;
    }
}

