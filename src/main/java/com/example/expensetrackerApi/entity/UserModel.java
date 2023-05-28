package com.example.expensetrackerApi.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserModel {

	@NotBlank(message="Name must not be null")
	private String name;
	
	@NotBlank(message="Email must not be null")
	@Email(message="Enter a valid email address")
	private String email;
	

	private String password;
	private Long age=0L;
}
