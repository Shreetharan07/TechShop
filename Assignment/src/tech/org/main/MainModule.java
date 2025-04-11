package tech.org.main;

import tech.org.dao.*;
import tech.org.entity.*;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CustomerDAO customerDAO = new CustomerDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        InventoryDAO inventoryDAO = new InventoryDAOImpl();

        System.out.println("==== Welcome to TechShop ====");

        boolean exit = false;

        while (!exit) {
            System.out.println("\n----------------------------------------------------------------------------------------------");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customer");
            System.out.println("3. Add Product");
            System.out.println("4. View Product");
            System.out.println("5. Place Order");
            System.out.println("6. View Order");
            System.out.println("7. Update Order Status");
            System.out.println("8. Add Inventory");
            System.out.println("9. View All Inventory");
            System.out.println("10. Update Inventory Stock");
            System.out.println("11. Exit");
            System.out.println("Choose an option:");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1: // Add Customer
                        System.out.print("Customer ID: "); int custId = sc.nextInt(); sc.nextLine();
                        System.out.print("First Name: "); String fname = sc.nextLine();
                        System.out.print("Last Name: "); String lname = sc.nextLine();
                        System.out.print("Email: "); String email = sc.nextLine();
                        System.out.print("Phone: "); String phone = sc.nextLine();
                        System.out.print("Address: "); String address = sc.nextLine();
                        Customer customer = new Customer(custId, fname, lname, email, phone, address);
                        System.out.println(customerDAO.addCustomer(customer) ? "Added!" : "Failed.");
                        break;

                    case 2: // View Customer
                        System.out.print("Customer ID: ");
                        Customer c = customerDAO.getCustomerById(sc.nextInt());
                        if (c != null) {
                            System.out.println("Name: " + c.getFirstName() + " " + c.getLastName());
                            System.out.println("Email: " + c.getEmail());
                        } else System.out.println("Customer not found.");
                        break;

                    case 3: // Add Product
                        System.out.print("Product ID: "); int pid = sc.nextInt(); sc.nextLine();
                        System.out.print("Name: "); String pname = sc.nextLine();
                        System.out.print("Desc: "); String desc = sc.nextLine();
                        System.out.print("Price: "); double price = sc.nextDouble();
                        Product p = new Product(pid, pname, desc, price);
                        System.out.println(productDAO.addProduct(p) ? "Product added!" : "Failed.");
                        break;

                    case 4: // View Product
                        System.out.print("Product ID: ");
                        Product prod = productDAO.getProductById(sc.nextInt());
                        if (prod != null)
                            System.out.println("Name: " + prod.getProductName() + ", Price: " + prod.getPrice());
                        else System.out.println("Product not found.");
                        break;

                    case 5: // Place Order
                        System.out.print("Order ID: "); int oid = sc.nextInt();
                        System.out.print("Customer ID: "); int cid = sc.nextInt();
                        System.out.print("Total Amount: "); double total = sc.nextDouble();
                        Date date = new Date(System.currentTimeMillis());
                        Order o = new Order(oid, cid, date, total, "Pending");
                        System.out.println(orderDAO.placeOrder(o) ? " Order placed!" : " Failed.");
                        break;

                    case 6: // View Order
                        System.out.print("Order ID: ");
                        Order ord = orderDAO.getOrderById(sc.nextInt());
                        if (ord != null) {
                            System.out.println("Customer ID: " + ord.getCustomerID());
                            System.out.println("Total: ₹" + ord.getTotalAmount());
                        } else System.out.println("Order not found.");
                        break;

                    case 7: // Update Order Status
                        System.out.print("Order ID: "); int updateId = sc.nextInt(); sc.nextLine();
                        System.out.print("New Status: "); String newStat = sc.nextLine();
                        System.out.println(orderDAO.updateOrderStatus(updateId, newStat)
                                ? " Updated!" : " Failed.");
                        break;

                    case 8: // Add Inventory
                        System.out.print("Inventory ID: "); int invId = sc.nextInt();
                        System.out.print("Product ID: "); int prodId = sc.nextInt();
                        System.out.print("Quantity: "); int qty = sc.nextInt();
                        Date now = new Date(System.currentTimeMillis());
                        Inventory inv = new Inventory(invId, prodId, qty, now);
                        System.out.println(inventoryDAO.addInventoryItem(inv) ? " Added!" : " Failed.");
                        break;

                    

                   
                    case 9: // View All Inventory
                        List<Inventory> allInv = inventoryDAO.getAllInventory();
                        for (Inventory i : allInv) {
                            System.out.println("Product ID: " + i.getProductID() +
                                    ", Stock: " + i.getQuantityInStock() +
                                    ", Last Updated: " + i.getLastStockUpdate());
                        }
                        break;

                    case 10: // Update Inventory Stock
                        System.out.print("Product ID: "); int prId = sc.nextInt();
                        System.out.print("New Quantity: "); int newQty = sc.nextInt();
                        System.out.println(inventoryDAO.updateStock(prId, newQty)
                                ? " Inventory updated!" : " Failed.");
                        break;

                    case 11:
                        exit = true;
                        System.out.println(" Exiting TechShop. Have a great day!");
                        break;

                    default:
                        System.out.println(" Invalid option.");
                }

            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }

        sc.close();
    }
}
