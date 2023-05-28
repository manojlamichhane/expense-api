package com.example.expensetrackerApi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.expensetrackerApi.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String jwtToken = null;
		String username = null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
//			extracting token and username from jwt for validation
			
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);
			}catch(IllegalArgumentException e) {
				throw new RuntimeException("Unable to get JWT token");
			}catch(ExpiredJwtException e) {
				throw new RuntimeException("Jwt token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
			
			// extracting userdetails from the username
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// validating token
			
				if(jwtUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				}
				filterChain.doFilter(request, response);	
				}	
		}
