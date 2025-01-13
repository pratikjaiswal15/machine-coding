package foodordering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantManager {

    List<Restaurant> restaurants;

    public RestaurantManager() {
        this.restaurants = new ArrayList<>();
    }

    public void registerRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void updateMenu(Restaurant restaurant, List<MenuItem> newMenuItems) {
        List<MenuItem> currentMenu = restaurant.getMenu();

        for(MenuItem newItem : newMenuItems) {
            boolean itemsExists = false;
            for(MenuItem existingItem : currentMenu) {
                if(existingItem.getName().equals(newItem.getName())) {
                    existingItem.setPrice(newItem.getPrice());
                    itemsExists = true;
                    break;
                }
            }

            if(!itemsExists) {
                currentMenu.add(newItem);
            }

        }
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }

    public List<MenuItem> getALlMenuItems(){
        Set<MenuItem> allItems = new HashSet<>();

        for(Restaurant restaurant : restaurants)  {
            allItems.addAll(restaurant.getMenu());
        }

        return new ArrayList<>(allItems);
    }


}
