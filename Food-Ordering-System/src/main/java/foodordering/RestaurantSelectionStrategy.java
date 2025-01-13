package foodordering;

import java.util.List;

public interface RestaurantSelectionStrategy {

    Restaurant selectRestaurant(List<Restaurant> restaurants, Order order);
}
