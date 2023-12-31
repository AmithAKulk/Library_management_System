package com.example.library_management_system.service;

import com.example.library_management_system.DTO.responseDTO.ResponseLibraryCard;
import com.example.library_management_system.DTO.responseDTO.ResponseStudent;
import com.example.library_management_system.DTO.responseDTO.ResponseStudentWhenAdded;
import com.example.library_management_system.DTO.requestDTO.RequestStudent;
import com.example.library_management_system.Enum.CardStatus;
import com.example.library_management_system.Enum.Gender;
import com.example.library_management_system.converters.StudentConverter;
import com.example.library_management_system.exception.StudentNotFoundException;
import com.example.library_management_system.mail.MailComposer;
import com.example.library_management_system.model.LibraryCard;
import com.example.library_management_system.repository.StudentRepository;
import com.example.library_management_system.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public ResponseStudentWhenAdded addStudent(RequestStudent requestStudent) {
        Student student = StudentConverter.fromReqStudentToStudent(requestStudent);

        LibraryCard libraryCard = LibraryCard.builder().
                cardNo(String.valueOf(UUID.randomUUID())).
                cardStatus(CardStatus.ACTIVE).
                build();

        libraryCard.setStudent(student);
        student.setLibraryCard(libraryCard);
        Student savedStudent = studentRepository.save(student);

        ResponseStudentWhenAdded responseStudentWhenAdded = StudentConverter.fromStudentToResponseStudentWhenAdded(student);

        ResponseLibraryCard responseLibraryCard = ResponseLibraryCard.builder().
                cardNo(savedStudent.getLibraryCard().getCardNo()).
                cardStatus(savedStudent.getLibraryCard().getCardStatus()).
                issueDate(savedStudent.getLibraryCard().getIssueDate()).
                build();

        responseStudentWhenAdded.setResponseLibraryCard(responseLibraryCard);

        // send mail
        SimpleMailMessage message = MailComposer.composeAddStudentEmail(savedStudent);
        javaMailSender.send(message);

        return responseStudentWhenAdded;
    }

    public ResponseStudent getStudent(int regNo) {
        Optional<Student> studentOptional = studentRepository.findById(regNo);
        if(!studentOptional.isPresent()){
            throw  new StudentNotFoundException("Student does not exists.");
        }
        Student student = studentOptional.get();
        ResponseStudent responseStudent = StudentConverter.fromStudentResStudent(student);
        return responseStudent;
    }

    public ResponseEntity<String> deleteStudent(int regNo) {
        Optional<Student> student = studentRepository.findById(regNo);
        if(!student.isPresent()){
            throw new StudentNotFoundException("Student does not exists.");
        }
        studentRepository.deleteById(regNo);
        return new ResponseEntity<>("Student deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> updateStudent(int regNo, int newAge) {
        Optional<Student> student = studentRepository.findById(regNo);
        if(student.isPresent()){
            Student obj = student.get();
            obj.setAge(newAge);
            studentRepository.save(obj);
            return new ResponseEntity<>("Age updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Id", HttpStatus.BAD_REQUEST);
    }

    public List<ResponseStudent> getAllStudents() {
        List<Student> list = studentRepository.findAll();
        List<ResponseStudent> allStudents = new ArrayList<>();
        for(Student student : list){
            ResponseStudent responseStudent = StudentConverter.fromStudentResStudent(student);
            allStudents.add(responseStudent);
        }
        return allStudents;
    }

    public List<ResponseStudent> getAllMaleStudents() {
//      List<Student> allStudents = studentRepository.findAll(); // Finding all students using default method
        List<Student> maleStudentObjects = studentRepository.findByGender(Gender.MALE); // finding only male students using custom method
        List<ResponseStudent> maleStudents = new ArrayList<>();
        for(Student student : maleStudentObjects){
            ResponseStudent responseStudent = StudentConverter.fromStudentResStudent(student);
            maleStudents.add(responseStudent);
        }
        return maleStudents;
    }
}