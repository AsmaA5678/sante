package com.backend.sante.exceptions;

public class JeuneException extends Exception {

    public JeuneException() {
        super();
    }

    public JeuneException(String message) {
        super(message);
    }

    public JeuneException(String message, Throwable cause) {
        super(message, cause);
    }

    public JeuneException(Throwable cause) {
        super(cause);
    }
}

