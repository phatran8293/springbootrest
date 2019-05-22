package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@CrossOrigin
@RestController
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/students")
  public List<Student> getStudents() {
	  return studentRepository.findAll();
  }
  
  @GetMapping("/student/{id}")
  public Student getStudentById(@PathVariable("id") int id) {
      return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
  }
  
  @DeleteMapping("/student/{id}")
  public void delStudentById(@PathVariable("id") int id) {
      studentRepository.deleteById(id);
  }
  
  @ResponseBody @PutMapping(value="/student/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Student> update(@PathVariable int id, @Valid @RequestBody Student student, Errors error) {
      if (error.hasErrors()) {
    	  System.out.println("Error");
      }
	  if (!studentRepository.findById(id).isPresent()) {
          ResponseEntity.badRequest().build();
      }
	  student.setId(id);
      return ResponseEntity.ok(studentRepository.save(student));
  }
  
  @ResponseBody @PostMapping(value="/student/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public long createStudent(@RequestBody Student student) {
  	Student savedStudent = studentRepository.save(student);
  	return savedStudent.getId();
  }
  
}
