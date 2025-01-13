package org.example;

import java.time.LocalTime;
import java.util.Objects;

public class Slot {
    private LocalTime startTime;
    private LocalTime endTime;

    public Slot(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Slot slot = (Slot) obj;
        return startTime.equals(slot.startTime) && endTime.equals(slot.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return startTime + " to " + endTime;
    }
}
