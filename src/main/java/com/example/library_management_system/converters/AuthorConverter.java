package com.example.library_management_system.converters;

import com.example.library_management_system.DTO.responseDTO.ResponseAuthor;
import com.example.library_management_system.DTO.requestDTO.RequestAuthor;
import com.example.library_management_system.model.Author;
import com.example.library_management_system.model.Student;

public class AuthorConverter {

    public static Author fromRequestAuthorToAuthor(RequestAuthor requestAuthor){
        return  Author.builder().
                name(requestAuthor.getName()).
                age(requestAuthor.getAge()).
                email(requestAuthor.getEmail()).
                build();
    }
    public static ResponseAuthor fromAuthorToResponseAuthor(Author author){
        return  ResponseAuthor.builder().
                name(author.getName()).
                age(author.getAge()).
                email(author.getEmail()).
                id(author.getId()).
                build();
    }
}