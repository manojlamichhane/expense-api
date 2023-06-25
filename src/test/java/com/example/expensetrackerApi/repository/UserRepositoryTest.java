package com.example.expensetrackerApi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.expensetrackerApi.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository; 
	
	@Test
	void shouldTestIfTheUserExistsByEmail() {
		
//		given
		User testUser = new User(1L,"Manoj","manoj@manoj.com",",manoj",30L,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));
		userRepository.save(testUser);
		
//		when
		boolean respone = userRepository.existsByEmail("manoj@manoj.com");
		
//		then
		assertThat(respone).isTrue();

	}
//	@BeforeEach
//	void setUp() {
//		Authentication authentication = Mockito.mock(Authentication.class);
//		// Mockito.whens() for your authorization object
//		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//		SecurityContextHolder.setContext(securityContext);
//	}
	
	
	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}
	
	@Test
	void shouldTestTheSearchOfUserByEmail() {
//		given
		User testUser = new User(1L,"Manoj","manoj@manoj.com",",manoj",30L,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));
		userRepository.save(testUser);
				
//		when
		Optional<User> result = userRepository.findByEmail("manoj@manoj.com");
		if(result.isPresent()) {
			User newUser = result.get();
			assertThat(newUser.getId()).isEqualTo(testUser.getId());
		}
		
//		then

	}

}


