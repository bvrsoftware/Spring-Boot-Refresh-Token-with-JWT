package com.example.demo.controllers;

import com.example.demo.entity.Student;
import com.example.demo.payloads.StudentDto;
import com.example.demo.services.impl.StudentServiceImp;
import com.example.demo.services.impl.customStudentDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StudentController {

    @Autowired
    private customStudentDetailsService serviceImp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/")
    public ResponseEntity<List<Student>> getAllStudent(){
        return ResponseEntity.ok(this.serviceImp.getAllStudent());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {

        return new ResponseEntity<>(this.serviceImp.getById(id), HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> removeStudent(@PathVariable Integer id) {
        return new ResponseEntity<>(this.serviceImp.removeStudent(id),HttpStatus.OK);
    }
    @PostMapping(value ="/" )
    public ResponseEntity<Student> saveStudent(@RequestBody StudentDto student) {
        StudentDto studentDto=new StudentDto();
        studentDto.setRole(student.getRole());
        studentDto.setEmail(student.getEmail());
        studentDto.setPassword(passwordEncoder.encode(student.getPassword()));
        studentDto.setDob(student.getDob());
        studentDto.setFullname(student.getFullname());
       Student student1= this.serviceImp.saveStudent(this.modelMapper.map(studentDto,Student.class));
       // System.out.println(student1.toString());
        return new ResponseEntity<Student>(student1,HttpStatus.CREATED);
    }
}
