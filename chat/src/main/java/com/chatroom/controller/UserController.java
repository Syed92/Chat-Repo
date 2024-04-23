package com.chatroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatroom.entity.User;
import com.chatroom.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/add")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		log.info("saving user details {}", user.getUsername());
		user = userService.saveUser(user);

		return ResponseEntity.ok(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {

		String password = user.getPassword();
		user = userService.getUserDetails(user);

		if (user != null && user.getPassword().equalsIgnoreCase(password)) {
			return new ResponseEntity<String>("login succesful", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("invalid credentials", HttpStatus.BAD_REQUEST);
		}
	}

}
