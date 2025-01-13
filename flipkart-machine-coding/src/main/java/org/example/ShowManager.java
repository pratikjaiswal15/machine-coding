package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowManager {
    private Map<String, Show> shows = new HashMap<>();
    private RankingStrategy rankingStrategy;

    public ShowManager(RankingStrategy rankingStrategy) {
        this.rankingStrategy = rankingStrategy;
    }

    public void setStrategy(RankingStrategy strategy) {
        this.rankingStrategy = strategy;
    }

    public void registerShow(String showName, String genre) {
        Show show = new Show(showName, genre);
        shows.put(showName, show);
        System.out.println(showName + " show is registered !!");
    }

    public void onboardShowSlots(String showName, List<Slot> slots, List<Integer> capacities) {
        Show show = shows.get(showName);
        if (show != null) {
            for (int i = 0; i < slots.size(); i++) {
                if (!show.addSlot(slots.get(i), capacities.get(i))) {
                    System.out.println("Slot already exists: " + slots.get(i));
                }
            }
            System.out.println("Done!");
        } else {
            System.out.println("Show not found.");
        }
    }

    public void searchShowsByGenre(String genre) {
        List<Show> genreShows = new ArrayList<>();
        for (Show show : shows.values()) {
            if (show.getGenre().equalsIgnoreCase(genre)) {
                genreShows.add(show);
            }
        }

        List<Show> rankedShows = rankingStrategy.rank(genreShows);
        for (Show show : rankedShows) {
            System.out.println(show.getName() + ": " + show.getAvailableSlots());
        }
    }

    public boolean bookTicket(String userName, String showName, Slot slot, int numTickets) {
        Show show = shows.get(showName);
        if (show != null) {
            return show.bookSlot(userName, slot, numTickets);
        }
        return false;
    }

    public boolean cancelBookingById(int bookingId) {
        return true;
    }
}
