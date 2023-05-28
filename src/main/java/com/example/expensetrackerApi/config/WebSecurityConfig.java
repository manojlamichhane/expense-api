package com.example.expensetrackerApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.expensetrackerApi.security.CustomUserDetailsService;
import com.example.expensetrackerApi.security.JwtRequestFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetails;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.csrf().disable()
		 		.authorizeHttpRequests().requestMatchers("/login", "/register").permitAll().and()
				.authorizeHttpRequests().anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		 return	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic().and().build();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authenticationProvider());
		return authenticationManagerBuilder.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetails);
		
//		authenticationProvider.setUserDetailsService(userDetailsService());
		
		authenticationProvider.setPasswordEncoder(encoder());
		return authenticationProvider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.withUsername("admin").password(encoder().encode("admin")).roles("ADMIN")
				.build();
		UserDetails user = User.withUsername("user").password(encoder().encode("user")).roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}

//	password encrytping is a must
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public JwtRequestFilter authenticationJwtTokenFilter() {
//	return new JwtRequestFilter();	
//	}

}
