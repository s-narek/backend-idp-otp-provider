package ru.tecforce.identity.exception;

import lombok.Getter;

@Getter
public class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super(message);
    }
}
