package tech.org.dao;

import tech.org.entity.OrderDetail;
import java.util.List;

public interface OrderDetailDAO {
    boolean addOrderDetail(OrderDetail detail);
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}
