package ru.isands.test.estore.exception;

public class TooManyEntitiesExistsException extends RuntimeException{
    public TooManyEntitiesExistsException(String message) {
        super(message);
    }
}
