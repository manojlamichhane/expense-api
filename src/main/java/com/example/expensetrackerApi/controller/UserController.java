package com.example.expensetrackerApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetrackerApi.Service.UserService;
import com.example.expensetrackerApi.entity.User;
import com.example.expensetrackerApi.exception.ResourceNotFoundException;


@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/profile")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUser() {
		return userService.getUser();
	}
	
	@PutMapping("/update")
	@ResponseStatus(value = HttpStatus.OK)
	public void updateUser(@RequestBody User user) throws ResourceNotFoundException {
		userService.updateUser(user);
	}

	@DeleteMapping("/deactivate")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUser() throws ResourceNotFoundException{
		userService.deleteUser();
	}
}
