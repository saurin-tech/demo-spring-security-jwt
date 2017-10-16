package com.example.demo.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import static com.example.demo.jwt.JWTConstants.HEADER_STRING;
import static com.example.demo.jwt.JWTConstants.SECRET;
import static com.example.demo.jwt.JWTConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);
		
		if(header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		CustomAuthenticationToken authentication = getAuthentication(request);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	private CustomAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if(token != null) {
			//parse the token
			String user = Jwts.parser()
					.setSigningKey(SECRET.getBytes())
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey("secret".getBytes())
					  .parseClaimsJws(token);
			
			user = claims.getBody().getSubject();
			System.out.println("=======> Printing the User in the GetAuthentication: " + user);	
			String db = (String) claims.getBody().get("db");
			System.out.println("=======> Printing the Db in the GetAuthentication: " + db);
			
			if(user != null) {
				return new CustomAuthenticationToken(user, null, new ArrayList<>(), db);
				//return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
