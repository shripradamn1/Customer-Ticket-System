package com.spring_boot.CSTS.Exception;

public class InvalidNotificationException extends RuntimeException {
    public InvalidNotificationException(String message) {
        super(message);
    }
}
