package tech.org.dao;

import tech.org.entity.Inventory;
import java.util.List;

public interface InventoryDAO {
    boolean addInventoryItem(Inventory inventory);
    Inventory getInventoryByProductId(int productId);
    boolean updateStock(int productId, int newQuantity);
    List<Inventory> getAllInventory();
}
