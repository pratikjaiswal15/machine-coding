package foodordering;

import java.util.List;

public class OrderService {

    private RestaurantManager restaurantManager;
    private RestaurantSelectionStrategy restaurantSelectionStrategy;

    public OrderService(RestaurantManager restaurantManager, RestaurantSelectionStrategy restaurantSelectionStrategy) {
        this.restaurantManager = restaurantManager;
        this.restaurantSelectionStrategy = restaurantSelectionStrategy;
    }

    public void setRestaurantSelectionStrategy(RestaurantSelectionStrategy strategy) {
        this.restaurantSelectionStrategy = strategy;
    }

    public boolean placeOrder(Order order) {
        List<Restaurant> restaurants = restaurantManager.getAllRestaurants();
        Restaurant selectedRestaurant = restaurantSelectionStrategy.selectRestaurant(restaurants, order);

        if(selectedRestaurant != null) {
            selectedRestaurant.reduceCapacity(order.getTotalItems());
            System.out.println("Order placed with restaurant: " + selectedRestaurant.getName());
            return true;
        }

        return false;
    }
}
