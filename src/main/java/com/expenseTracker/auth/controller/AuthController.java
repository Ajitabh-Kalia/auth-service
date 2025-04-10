package com.expenseTracker.auth.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.auth.model.User;
import com.expenseTracker.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return authService.register(user);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginData) {
		Optional<String> token = authService.login(loginData.getUsername(), loginData.getPassword());
		if(token.isPresent()) {
			return ResponseEntity.ok(Collections.singletonMap("token", token.get()));
		}
		else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
	}
	
	@PostMapping("/registerAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerAdmin(@RequestBody User user){
		authService.registerAdmin(user);
		return ResponseEntity.ok("Registered a new Admin account.");
	}

}
