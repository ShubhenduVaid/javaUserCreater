package com.faker.UserCreater.controller;

import com.faker.UserCreater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/generate-users")
	public String generateUsers() {
		userService.generateAndPostUsers(1000000); // Generate 1000 users
		return "User generation started!";
	}
}
