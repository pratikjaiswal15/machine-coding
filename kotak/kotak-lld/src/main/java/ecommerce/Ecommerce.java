/*

Design a more  shopping cart system that supports:
A Product class with additional attributes like product category,
 SKU, and discount strategies (e.g., percentage or flat rate discounts).
A Cart class that can handle multiple products,
and provide methods for adding/removing products, applying discounts,
and calculating the total cost with optional promotions and taxes.
A Discount class that allows for different discount types
 (e.g., percentage-based, fixed amount, buy-one-get-one-free), and integrates with products.
A Coupon class that allows applying a coupon code to the cart, affecting the total price.
A Customer class that tracks customer details, shipping address, and purchase history, and can apply different loyalty rewards based on previous purchases.
 */

/*

Entities

Customer - Id, name, email, phone, verified
Address - Id, first_line, second_line, pincode, landmark, customerId
Product - Id, name, category, SKU, price, discountId
Cart - Id, customerId, totalPrice, timestamp
ProductCart - Id, cartId, productId, price, timestamp, delivery_status
Discount - Id, type, rate
Coupon - Id, name, discount
Checkout - Id, customerId, cartId, couponId, timestamp, paymentGatewayId, status

API design

 */


// strategy pattern for discount
package ecommerce;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ecommerce {

    public static void main(String[] args) {

        Product pillow = new Product("3", "pillow", "furniture", 100, 100);
        List<Product> productList = List.of(new Product("1", "ihphone16", "phone", 100, 10000));
        productList.add(new Product("2", "Pen", "stationary", 1000, 10));
        Customer customer = new Customer("pratik");
        Cart cart = new Cart("1", customer, 1000, LocalDateTime.now());
        ProductCart productCart = new ProductCart("1", productList.get(0), cart, 100, LocalDateTime.now());

        productCart.addProduct(pillow);

        List<Product> myProducts = productCart.getCartItems();

        for(Product myProductt : myProducts) {
            System.out.println(myProductt);
        }

    }
}


class Product {
    private String Id, name, category;
    private long SKU, price;
    private DiscountStrategy discountStrategy;

    public Product(String id, String name, String category, long SKU, long price) {
        Id = id;
        this.name = name;
        this.category = category;
        this.SKU = SKU;
        this.price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getSKU() {
        return SKU;
    }

    public void setSKU(long SKU) {
        this.SKU = SKU;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void addDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void updateProductPriceOnDiscount() {
        DiscountFactory.discountObject("percentage");
    }
}

class Cart {
    private String Id;
    private Customer customer;

    private double totalPrice;
    private LocalDateTime timestamp;



    public Cart(String id, Customer customer, double totalPrice, LocalDateTime timestamp) {
        Id = id;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
    }
}

class Customer {
  private String name;

    public Customer(String name) {
        this.name = name;
    }
}

class ProductCart {

    private String Id, delivery_status;
    private Product product;
    private Cart cart;
    private double price;
    private LocalDateTime timestamp;
    List<Product> cartItems;

    public ProductCart(String id, Product product, Cart cart, double price, LocalDateTime timestamp) {
        Id = id;
        this.product = product;
        this.cart = cart;
        this.price = price;
        this.timestamp = timestamp;
        this.delivery_status = "Not_ordered";
        cartItems = new ArrayList<>();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public List<Product>  getCartItems() {
        return cartItems;
    }
}

interface DiscountStrategy {
    double getDiscount();
}

// percentage-based, fixed amount, buy-one-get-one-free
class PercentageBasedDiscountStrategy implements DiscountStrategy {

    @Override
    public double getDiscount() {
        return 15;
    }
}

class FixedAmountDiscountStrategy implements DiscountStrategy {

    @Override
    public double getDiscount() {
        return 500;
    }
}

class BuyGetDiscountStrategy implements DiscountStrategy {

    @Override
    public double getDiscount() {
        return 1;
    }
}

class DiscountFactory {

    public static DiscountStrategy discountObject(String type) {

        DiscountStrategy discountStrategy = null;
        switch (type) {
            case "percentage": {
                discountStrategy = new PercentageBasedDiscountStrategy();
                break;
            }

            case "fixed": {
                discountStrategy = new FixedAmountDiscountStrategy();
                break;
            }

            case "buyGet": {
                discountStrategy = new BuyGetDiscountStrategy();
                break;
            }

            default :
                throw new IllegalArgumentException("Invalid discount type provided");

        }

        return discountStrategy;

    }
}