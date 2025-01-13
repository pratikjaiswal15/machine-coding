package uk.tw.energy.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

    private String message;
    private LocalDateTime localDateTime;

    public ErrorDetails(String message) {
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
