package com.example.demo.services.impl;

import com.example.demo.entity.Student;
import com.example.demo.repository.studentRepository;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class customStudentDetailsService implements UserDetailsService, StudentService {

    @Autowired
    private studentRepository studentRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Student student=studentRepository.findByEmail(username).get();
          if(student==null)
              throw new UsernameNotFoundException("User not Found !!!");
        return student;
    }

    @Override
    public Student getById(int id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public String removeStudent(int id) {
        String msg="";
        Student student=studentRepository.findById(id).get();
        if(student==null)
            msg="Record Not Found !!!";
        else
            msg="Record Successfully deleted !!!";
        return msg;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findByEmail(String email) {
        Student student=studentRepository.findByEmail(email).get();
        return student;
    }
}
