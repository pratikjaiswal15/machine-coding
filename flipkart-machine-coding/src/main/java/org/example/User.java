package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private List<Booking> bookings = new ArrayList<>();

    public User(String userName) {
        this.userName = userName;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void cancelBooking(Booking booking) {
        bookings.remove(booking);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}
