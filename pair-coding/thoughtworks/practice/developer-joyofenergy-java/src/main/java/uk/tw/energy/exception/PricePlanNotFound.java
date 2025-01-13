package uk.tw.energy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PricePlanNotFound extends RuntimeException{

    public PricePlanNotFound(String message) {
        super(message);
    }
}
