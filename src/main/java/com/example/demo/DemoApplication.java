package com.example.demo;

import com.example.demo.model.SignUpForm;
import com.example.demo.security.services.AuthAndAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private AuthAndAdminService authService;


	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws Exception {
		log.info("Application is ready to use");

		SignUpForm signUpForm = new SignUpForm();
		signUpForm.setEmail("admin@organization.com");
		signUpForm.setName("admin user");
		signUpForm.setUsername("admin");
		Set<String> roleSet = new HashSet<>();
		roleSet.add("ADMIN");
	//	roleSet.add("USER");
		signUpForm.setRole(roleSet);
		signUpForm.setPassword("123456");
		authService.registerUser(signUpForm);
		log.info("Creating Admin for app");

		final SignUpForm userForm = new SignUpForm();
		userForm.setEmail("appuser@soemdomain.com");
		userForm.setName("app user");
		userForm.setUsername("appuser");
		Set<String> userRole = new HashSet<>();
		userRole.add("USER");
		userForm.setRole(userRole);
		userForm.setPassword("appus3r");
		authService.registerUser(userForm);
		log.info("Creating An User for app");

	}

}
