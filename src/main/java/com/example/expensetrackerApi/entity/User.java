package com.example.expensetrackerApi.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table ( name="tbl_users") 
@Data

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name cannot be Null")
	private String name;
	
	@NotBlank(message = "Email cannot be Null")
	@Email(message = "Please enter a valid Email Address")
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Long age;
	
	@Column(nullable = false,updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false,updatable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;
}
