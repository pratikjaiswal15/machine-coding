package foodordering;

import java.util.List;

public class LowerPriceSelectionStrategy implements RestaurantSelectionStrategy{
    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurants, Order order) {
        Restaurant selectedRestaurant = null;
        double minCost = Double.MAX_VALUE;

        for(Restaurant restaurant : restaurants) {
            if(restaurant.canFulfilOrder(order)) {
                double totalCost = calculateOrderCost(restaurant, order);
                if(totalCost < minCost) {
                    minCost = totalCost;
                    selectedRestaurant = restaurant;
                }
            }
        }

        return selectedRestaurant;
    }

    private double calculateOrderCost(Restaurant restaurant, Order order) {
        double cost = 0;

        for(MenuItem item : order.getItems()) {
            for(MenuItem menuItem : restaurant.getMenu()) {
                if(menuItem.equals(item)) {
                    cost += menuItem.getPrice();
                }
            }
        }

        return cost;
    }
}
