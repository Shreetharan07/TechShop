package tech.org.dao;

import tech.org.entity.Product;
import java.util.List;

public interface ProductDAO {
    boolean addProduct(Product product);
    Product getProductById(int productId);
    List<Product> getAllProducts();
    boolean updateProduct(Product product);
}
