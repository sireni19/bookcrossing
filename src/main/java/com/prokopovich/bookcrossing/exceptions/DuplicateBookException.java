package com.prokopovich.bookcrossing.exceptions;

public class DuplicateBookException extends Exception{
    public DuplicateBookException(String message) {
        super(message);
    }
}
