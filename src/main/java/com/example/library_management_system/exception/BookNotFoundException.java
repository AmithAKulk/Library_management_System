package com.example.library_management_system.exception;

public class BookNotFoundException extends  RuntimeException{
    public BookNotFoundException(String message){
        super(message);
    }
}
