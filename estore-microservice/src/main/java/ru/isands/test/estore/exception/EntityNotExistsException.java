package ru.isands.test.estore.exception;

public class EntityNotExistsException extends RuntimeException{
    public EntityNotExistsException(String message) {
        super(message);
    }
}
