package ru.tecforce.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionData {
    private String message;
    private String code;
    private Object data;

    public ExceptionData(String message) {
        this.message = message;
    }

    public ExceptionData(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
