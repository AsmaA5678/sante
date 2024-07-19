package com.backend.sante.exceptions;

public class UserNameNotFoundException  extends Exception {

    public UserNameNotFoundException() {super();}

    public UserNameNotFoundException(String message) {
        super(message);
    }

    public UserNameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameNotFoundException(Throwable cause) {
        super(cause);
    }
}