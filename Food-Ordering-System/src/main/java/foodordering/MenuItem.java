package foodordering;

import java.util.Objects;

public class MenuItem {

    private String name;
    private Double price;

    public MenuItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name) && Objects.equals(price, menuItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
