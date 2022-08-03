package com.grocery;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.grocery.model.Role;
import com.grocery.model.User;
import com.grocery.service.UserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GroceryBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryBackEndApplication.class, args);
	}

	@Bean	
	CommandLineRunner runner(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new User(null, "Admin", "Admin", "admin@admin.com", "admin1234", User.Gender.MALE,
					new ArrayList<>(), 1234567890, null));
			userService.saveUser(new User(null, "Admin", "User", "admin@user.com", "admin1234", User.Gender.MALE,
					new ArrayList<>(), 1234567899, null));
			
			userService.addRoleToUser("admin@admin.com", "ROLE_ADMIN");
			userService.addRoleToUser("admin@user.com", "ROLE_USER");
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
