package org.example;

import java.util.Comparator;
import java.util.List;

public class RankByStartTime implements RankingStrategy {
    @Override
    public List<Show> rank(List<Show> shows) {
        // Sort shows based on the earliest available slot's start time
        shows.sort((show1, show2) -> {
            // Get the earliest slot in each show
            Slot earliestSlot1 = show1.getAvailableSlots().keySet()
                    .stream()
                    .min(Comparator.comparing(Slot::getStartTime))
                    .orElse(null);

            Slot earliestSlot2 = show2.getAvailableSlots().keySet()
                    .stream()
                    .min(Comparator.comparing(Slot::getStartTime))
                    .orElse(null);

            // Handle null values (if any show doesn't have available slots)
            if (earliestSlot1 == null && earliestSlot2 == null) return 0;
            if (earliestSlot1 == null) return 1;
            if (earliestSlot2 == null) return -1;

            // Compare the slots based on their start times
            return earliestSlot1.getStartTime().compareTo(earliestSlot2.getStartTime());
        });

        return shows;
    }
}
