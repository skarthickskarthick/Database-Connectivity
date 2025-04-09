package com.example.Mapping;


import com.example.Mapping.Principal;
import com.example.Mapping.School;
import com.example.Mapping.Subject;
import com.example.Mapping.Teacher;
import com.example.Mapping.SchoolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MappingApplication implements CommandLineRunner {

	@Autowired
	private SchoolRepository schoolRepository;

	public static void main(String[] args) {
		SpringApplication.run(MappingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create school
		School school = new School();
		school.setName("Greenwood High");

		// Principal
		Principal principal = new Principal();
		principal.setName("Mr. Sharma");
		school.setPrincipal(principal);

		// Teacher
		Teacher teacher = new Teacher();
		teacher.setName("Alice");
		teacher.setSchool(school);

		// Subject
		Subject subject = new Subject();
		subject.setName("Math");

		// Setup bidirectional Many-to-Many
		teacher.getSubjects().add(subject);
		subject.getTeachers().add(teacher);

		// Add teacher to school
		school.getTeachers().add(teacher);

		// Save everything via cascade
		schoolRepository.save(school);

		System.out.println("✅ Data saved successfully!");
	}
}
//http://localhost:8080/h2-console