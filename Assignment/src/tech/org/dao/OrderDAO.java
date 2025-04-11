package tech.org.dao;

import tech.org.entity.Order;
import java.util.List;

public interface OrderDAO {
    boolean placeOrder(Order order);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    boolean updateOrderStatus(int orderId, String newStatus);
}
