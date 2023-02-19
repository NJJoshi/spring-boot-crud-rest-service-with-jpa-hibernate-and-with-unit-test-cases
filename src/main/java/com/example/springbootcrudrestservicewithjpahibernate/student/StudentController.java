package com.example.springbootcrudrestservicewithjpahibernate.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Long id) {
        Optional<Student> result = studentRepository.findById(id);
        if(result.isEmpty()) {
            throw new StudentNotFoundException("Id:" + id);
        }
        return result.get();
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }

    @PostMapping("/student")
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        Student persistedStudent = studentRepository.save(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(persistedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if(existingStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        student.setId(id);
        studentRepository.save(student);
        return ResponseEntity.noContent().build();
    }
}
