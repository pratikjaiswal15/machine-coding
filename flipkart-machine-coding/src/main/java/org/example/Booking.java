package org.example;

public class Booking {

    private String userName;
    private Show show;
    private Slot slot;
    private int numTickets;

    public Booking(String userName, Show show, Slot slot, int numTickets) {
        this.userName = userName;
        this.show = show;
        this.slot = slot;
        this.numTickets = numTickets;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userName='" + userName + '\'' +
                ", show=" + show +
                ", slot=" + slot +
                ", numTickets=" + numTickets +
                '}';
    }
}
