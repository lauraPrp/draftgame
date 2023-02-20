package com.bebbold.draftgame.exception;

public class InvalidParamException extends Exception{
    private String message;

    public InvalidParamException(String message1) {

        this.message = message1;
    }
}
