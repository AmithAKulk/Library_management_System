package com.example.library_management_system.exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message){
        super(message);
    }
}
