package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.services.impl.customStudentDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TeckobitApplication implements CommandLineRunner {

	@Autowired
	private customStudentDetailsService studentDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(TeckobitApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper(){
		return  new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		Student student1=new Student();
		student1.setFullname("Sanjeev kumar");
		student1.setEmail("ssp9448@gmail.com");
		student1.setDob("24/08/1998");
		student1.setRole("ADMIN");
		student1.setPassword(this.passwordEncoder.encode("12345"));
		this.studentDetailsService.saveStudent(student1);
		Student student2=new Student();
		student2.setFullname("Rajeev kumar");
		student2.setEmail("rajeev@gmail.com");
		student2.setDob("24/08/1999");
		student2.setRole("USER");
		student2.setPassword(this.passwordEncoder.encode("12345"));
		this.studentDetailsService.saveStudent(student2);
	}
}
