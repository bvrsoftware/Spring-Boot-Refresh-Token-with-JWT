package com.example.demo.services.impl;

import com.example.demo.entity.Student;

import com.example.demo.services.StudentService;

import org.hibernate.dialect.Dialect;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

	@Override
	public Student getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getAllStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeStudent(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student findByEmail(String email) {
		return null;
	}
	/*
	 * List<Student> stdlist=new ArrayList<>(); public StudentServiceImp() {
	 * stdlist.add(new Student(stdlist.size() + 1, "Sanjeev kumar",
	 * "ssp9448@gmail.com", "12345", "24/08/1998")); stdlist.add(new
	 * Student(stdlist.size() + 1, "Rajeev Kumar", "rk9857@gmail.com", "1478",
	 * "17/07/1999")); stdlist.add(new Student(stdlist.size() + 1, "amit kumar",
	 * "amit12@gmail.com", "2580", "08/09/2000")); stdlist.add(new
	 * Student(stdlist.size() + 1, "sukhveer yadav", "sukhveer12@gmail.com", "1236",
	 * "08/09/2002")); } public Student getById(int id) { Student student=
	 * stdlist.get(id-1); return student; } public List<Student> getAllStudent() {
	 * 
	 * return stdlist; } public String removeStudent(int id) { String msg="";
	 * if(stdlist.remove(this.getById(id))) msg="delete successfully"; else
	 * msg="record not found"; return msg; } public Student saveStudent(Student
	 * student) { Student stdStudent=new
	 * Student(stdlist.get(stdlist.size()-1).getId()+1, student.getFullname(),
	 * student.getEmail(), student.getPassword(), student.getDob());
	 * stdlist.add(stdStudent); return stdStudent; }
	 */
}
