package com.recime.Cookbook.exception;

public class BadRecipeException extends RuntimeException{
    public BadRecipeException(String message) {
        super(message);
    }
}
