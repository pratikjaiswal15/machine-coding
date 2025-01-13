package org.example;

import java.time.LocalTime;
import java.util.*;


public class Show {
    private String name;
    private String genre;
    private Map<Slot, Integer> availableSlots = new HashMap<>();
    private Map<Slot, Integer> bookedSlots = new HashMap<>();
    private Map<Slot, Queue<String>> waitlist = new HashMap<>();

    public Show(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public boolean addSlot(Slot slot, int capacity) {
        if (availableSlots.containsKey(slot)) {
            return false;
        }
        availableSlots.put(slot, capacity);
        bookedSlots.put(slot, 0);
        waitlist.put(slot, new LinkedList<>());
        return true;
    }

    public boolean bookSlot(String userName, Slot slot, int numTickets) {
        if (availableSlots.containsKey(slot)) {
            int booked = bookedSlots.get(slot);
            int available = availableSlots.get(slot);

            if (booked + numTickets <= available) {
                bookedSlots.put(slot, booked + numTickets);
                return true;
            } else {
                waitlist.get(slot).add(userName);
                return false;
            }
        }
        return false;
    }

    public boolean cancelBooking(String userName, Slot slot) {
        if (bookedSlots.containsKey(slot)) {
            int booked = bookedSlots.get(slot);
            if (booked > 0) {
                bookedSlots.put(slot, booked - 1);

                if (!waitlist.get(slot).isEmpty()) {
                    String nextUser = waitlist.get(slot).poll();
                    bookedSlots.put(slot, bookedSlots.get(slot) + 1);
                    System.out.println("Transferred booking to " + nextUser);
                }
                return true;
            }
        }
        return false;
    }

    public Map<Slot, Integer> getAvailableSlots() {
        return availableSlots;
    }
}
