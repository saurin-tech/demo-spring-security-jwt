package com.example.demo.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.model.ApplicationUser;
import com.example.demo.repo.ApplicationUserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.example.demo.jwt.JWTConstants.EXPIRATION_TIME;
import static com.example.demo.jwt.JWTConstants.HEADER_STRING;
import static com.example.demo.jwt.JWTConstants.SECRET;
import static com.example.demo.jwt.JWTConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	private ApplicationUserRepo appUserRepo;
	
	@Autowired
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationUserRepo appUserRepo) {
		this.authenticationManager = authenticationManager;
		this.appUserRepo = appUserRepo;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ApplicationUser creds = new ObjectMapper()
					.readValue(request.getInputStream(), ApplicationUser.class);
			
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getUsername(),
							creds.getPassword(),
							new ArrayList<>())
					);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = authResult.getName();
		ApplicationUser user = appUserRepo.findByUsername(username);
		
		String token = Jwts.builder()
				.setSubject(username)
				.claim("db", user.getDbId())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
				.compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
