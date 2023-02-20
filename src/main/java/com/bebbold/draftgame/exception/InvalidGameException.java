package com.bebbold.draftgame.exception;

public class InvalidGameException extends Exception{
    private String message;

    public InvalidGameException(String message) {
        this.message = message;
    }
}
