package com.example.library_management_system.converters;

import com.example.library_management_system.DTO.responseDTO.ResponseStudent;
import com.example.library_management_system.DTO.responseDTO.ResponseStudentWhenAdded;
import com.example.library_management_system.DTO.requestDTO.RequestStudent;
import com.example.library_management_system.model.Student;

public class StudentConverter {

    public static Student fromReqStudentToStudent(RequestStudent requestStudent){
        return  Student.builder().
                name(requestStudent.getName()).
                age(requestStudent.getAge()).
                email(requestStudent.getEmail()).
                gender(requestStudent.getGender()).
                build();
    }

    public static ResponseStudentWhenAdded fromStudentToResponseStudentWhenAdded(Student student){
        return  ResponseStudentWhenAdded.builder().
                name(student.getName()).
                age(student.getAge()).
                gender(student.getGender()).
                build();
    }

    public static ResponseStudent fromStudentResStudent(Student student){
        return ResponseStudent.builder().
                name(student.getName()).
                age(student.getAge()).
                gender(student.getGender()).
                build();
    }
}