package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static int bookingIdCounter = 1;

    public static void main(String[] args) {

        final String TMKOCShow = "TMKOC";
        String theSonuNigamLiveEvent = "The Sonu Nigam Live Event";
        String theArijitSinghLiveEvent = "The Arijit Singh Live Event";

        RankingStrategy rankingStrategy = new RankByStartTime();
        ShowManager manager = new ShowManager(rankingStrategy);

        manager.registerShow(TMKOCShow, "Comedy");
        
        onboardShowSlots(manager, TMKOCShow, "09:00-10:00, 12:00-13:00, 15:00-16:00", "3, 2, 5");

        manager.registerShow(theSonuNigamLiveEvent, "Singing");

        onboardShowSlots(manager, theSonuNigamLiveEvent, "10:00-11:00, 13:00-14:00, 17:00-18:00", "3, 2, 1");

        manager.searchShowsByGenre("Comedy");

        processBookTicket(manager, "UserA", TMKOCShow, "12:00", 2);
        processBookTicket(manager, "UserB", TMKOCShow, "12:00", 1);

        manager.cancelBookingById(1234);
        System.out.println("Booking Canceled");

        manager.searchShowsByGenre("Comedy");

        processBookTicket(manager, "UserB", TMKOCShow, "12:00", 1);

        manager.registerShow(theArijitSinghLiveEvent, "Singing");

        onboardShowSlots(manager, theArijitSinghLiveEvent, "11:00-12:00, 14:00-15:00", "3, 2");

        manager.searchShowsByGenre("Singing");
    }

    private static void onboardShowSlots(ShowManager manager, String showName, String slotsString, String capacitiesString) {
        String[] slotsArray = slotsString.split(", ");
        String[] capacitiesArray = capacitiesString.split(", ");
        List<Slot> slots = new ArrayList<>();
        List<Integer> capacities = new ArrayList<>();

        if (slotsArray.length != capacitiesArray.length) {
            System.out.println("Mismatch between the number of slots and capacities.");
            return;
        }

        for (int i = 0; i < slotsArray.length; i++) {
            String[] times = slotsArray[i].split("-");
            String startTimeStr = formatTime(times[0]);
            String endTimeStr = formatTime(times[1]);

            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            if (!endTime.minusHours(1).equals(startTime)) {
                System.out.println("Sorry, show timings are of 1 hour only");
                return;
            }

            int capacity = Integer.parseInt(capacitiesArray[i]);
            slots.add(new Slot(startTime, endTime));
            capacities.add(capacity);
        }

        manager.onboardShowSlots(showName, slots, capacities);
        System.out.println("Done!");
    }

    private static void processBookTicket(ShowManager manager, String userName, String showName, String time, int tickets) {
        LocalTime startTime = LocalTime.parse(formatTime(time));
        Slot slot = new Slot(startTime, startTime.plusHours(1));
        boolean booked = manager.bookTicket(userName, showName, slot, tickets);

        if (booked) {
            System.out.println("Booked. Booking id: " + (bookingIdCounter++));
        } else {
            System.out.println("Booking Id: " + (bookingIdCounter++) + ", Wait listing");
        }
    }

    private static String formatTime(String time) {
        if (time.length() == 4) {
            return "0" + time;
        }
        return time;
    }
}