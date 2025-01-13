package org.example;

import foodordering.*;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        MenuItem item1 = new MenuItem("Pizza", 500.0);
        MenuItem item2 = new MenuItem("Burger", 200.0);
        MenuItem item3 = new MenuItem("Pasta", 150.0);

        List<MenuItem> menu1 = List.of(item1, item2);  // Restaurant A has Pizza and Burger
        List<MenuItem> menu2 = List.of(item1, item2, item3); // Restaurant B has Pizza, Burger, Pasta

        Restaurant restaurant1 = new Restaurant("Restaurant A", menu1, 50, 4.5);
        Restaurant restaurant2 = new Restaurant("Restaurant B", menu2, 30, 4.0);

        RestaurantManager restaurantRegistry = new RestaurantManager();
        restaurantRegistry.registerRestaurant(restaurant1);
        restaurantRegistry.registerRestaurant(restaurant2);

        // Display all items available in the system (union of restaurant menus)
        List<MenuItem> allAvailableItems = restaurantRegistry.getALlMenuItems();
        System.out.println("All items available in the system:");
        for (MenuItem item : allAvailableItems) {
            System.out.println(item.getName() + " - RS " + item.getPrice());
        }

        // Create an order for Pizza and Pasta
        Order order = OrderFactory.createOrder(List.of(item1, item3));

        // Use the LowerCostSelectionStrategy initially
        RestaurantSelectionStrategy costStrategy = new LowerPriceSelectionStrategy();
        OrderService orderService = new OrderService(restaurantRegistry, costStrategy);
        orderService.placeOrder(order);  // Expected: Order placed with Restaurant B


    }

}

