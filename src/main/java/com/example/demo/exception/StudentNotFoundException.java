package com.example.demo.exception;

public class StudentNotFoundException extends RuntimeException{
	public StudentNotFoundException(int id) {
        super("Student id not found : " + id);
    }
}
