package com.backend.sante.exceptions;

public class PhoneNonValideException extends Exception {

    public PhoneNonValideException() {super();}

    public PhoneNonValideException(String message) {
        super(message);
    }

    public PhoneNonValideException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNonValideException(Throwable cause) {
        super(cause);
    }
}
