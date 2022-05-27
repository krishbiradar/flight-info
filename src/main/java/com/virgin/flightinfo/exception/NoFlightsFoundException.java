package com.virgin.flightinfo.exception;

public class NoFlightsFoundException extends RuntimeException {

    public NoFlightsFoundException(String message) {
        super(message);
    }
}
