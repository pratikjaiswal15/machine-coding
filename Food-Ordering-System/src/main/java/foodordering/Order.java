package foodordering;

import java.util.List;

public class Order {

    private List<MenuItem> items;
    private int totalItems;

    public Order(List<MenuItem> items) {
        this.items = items;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
