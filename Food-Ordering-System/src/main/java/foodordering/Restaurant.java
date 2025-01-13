package foodordering;

import java.util.List;

public class Restaurant {

    private String name;
    private List<MenuItem> menu;
    private int capacity;
    private double rating;

    public Restaurant(String name, List<MenuItem> menu, int capacity, double rating) {
        this.name = name;
        this.menu = menu;
        this.capacity = capacity;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean canFulfilOrder(Order order) {
        return hasAllItems(order) && capacity >= order.getTotalItems();
    }

    private boolean hasAllItems(Order order) {
        for(MenuItem menuItem : order.getItems()) {
            if(!menu.contains(menuItem)) {
                return false;
            }
        }
        return true;
    }

    public void reduceCapacity(int itemsProcessed) {
        this.capacity -= itemsProcessed;
    }
}
