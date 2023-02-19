package com.example.springbootcrudrestservicewithjpahibernate.controller;

import com.example.springbootcrudrestservicewithjpahibernate.student.Student;
import com.example.springbootcrudrestservicewithjpahibernate.student.StudentController;
import com.example.springbootcrudrestservicewithjpahibernate.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void testGetAllStudents() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                                .get("/students")
                                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGetStudentById() throws Exception {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student(10001l, "Ranga", "E1234567")));
        RequestBuilder request = MockMvcRequestBuilders
                .get("/student/10001")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                            .andExpect(status().isOk())
                            .andExpect(content().json("{id: 10001,name: Ranga,passportNumber: E1234567}"))
                            .andReturn();
    }

    @Test
    public void testDeleteStudentById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/student/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }
}
