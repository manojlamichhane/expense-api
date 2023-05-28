package com.example.expensetrackerApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.expensetrackerApi.Service.UserService;
import com.example.expensetrackerApi.entity.AuthModel;
import com.example.expensetrackerApi.entity.UserModel;
import com.example.expensetrackerApi.security.CustomUserDetailsService;
import com.example.expensetrackerApi.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	UserService userService;
	
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void saveUser(@Valid @RequestBody UserModel user) {
		
		UserModel newUser = new UserModel();
		
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(encoder.encode(user.getPassword()));
		newUser.setAge(user.getAge());
		
		// password encoding for spring security
		
		userService.createUser(newUser);
	}
	
	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public String login(@RequestBody AuthModel authModel ) throws Exception {

		authentication(authModel.getEmail(),authModel.getPassword());
		
		UserDetails userdetails = userDetailsService.loadUserByUsername(authModel.getEmail());
		
		return jwtUtil.generateToken(userdetails);
		
		//	SecurityContextHolder.getContext().setAuthentication(authentication);	
	}
	

	private void authentication(String email,String password) throws Exception {
		try {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		
		}catch(DisabledException e) {
			throw new Exception("User disabled");
		}catch(BadCredentialsException e) {
			throw new Exception("Bad credentials");
		}
	}
}
