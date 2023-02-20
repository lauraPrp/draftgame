package com.bebbold.draftgame.exception;

public class NotFoundException extends Exception{
    private String message;

    public NotFoundException(String message) {

        this.message = message;
    }
}
