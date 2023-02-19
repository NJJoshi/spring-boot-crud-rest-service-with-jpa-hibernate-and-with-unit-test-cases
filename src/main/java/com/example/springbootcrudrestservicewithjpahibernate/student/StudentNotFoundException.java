package com.example.springbootcrudrestservicewithjpahibernate.student;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String exception) {
        super(exception);
    }
}
