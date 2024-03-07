package org.example.movierentalsjpa.common.model.exceptions;

public class ValidatorException extends MovieRentalsException {

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
