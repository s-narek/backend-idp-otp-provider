package ru.tecforce.identity.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final ExceptionData data;

    public ServiceException(String message) {
        this(new ExceptionData(message));
    }

    public ServiceException(String message, String code) {
        this(new ExceptionData(message, code));
    }

    public ServiceException(ExceptionData data) {
        super(data.getMessage());
        this.data = data;
    }
}
