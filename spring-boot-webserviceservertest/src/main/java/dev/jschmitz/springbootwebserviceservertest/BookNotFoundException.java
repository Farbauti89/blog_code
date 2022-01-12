package dev.jschmitz.springbootwebserviceservertest;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}