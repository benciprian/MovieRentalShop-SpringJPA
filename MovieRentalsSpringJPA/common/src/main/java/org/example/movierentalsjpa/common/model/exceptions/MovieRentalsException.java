package org.example.movierentalsjpa.common.model.exceptions;

public class MovieRentalsException extends RuntimeException{
    public MovieRentalsException(String message) {
        super(message);
    }

    public MovieRentalsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieRentalsException(Throwable cause) {
        super(cause);
    }
}
