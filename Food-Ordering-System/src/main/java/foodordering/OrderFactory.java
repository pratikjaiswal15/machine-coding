package foodordering;

import java.util.List;

public class OrderFactory {

    public static Order createOrder(List<MenuItem> items) {
        return new Order(items);
    }
}
