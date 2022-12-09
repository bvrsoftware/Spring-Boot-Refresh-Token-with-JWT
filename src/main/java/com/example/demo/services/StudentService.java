package com.example.demo.services;

import com.example.demo.entity.Student;

import java.util.List;

public interface StudentService {

    public Student getById(int id);
    public List<Student> getAllStudent();
    public String removeStudent(int id);
    public Student saveStudent(Student student);
    Student findByEmail(String email);
}
