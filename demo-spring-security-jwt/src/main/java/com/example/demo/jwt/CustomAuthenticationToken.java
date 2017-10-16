package com.example.demo.jwt;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = -6596053415797557738L;
	private String db;

	public CustomAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String db) {
		super(principal, credentials, authorities);
		this.db = db;
	}
	
	public String getDb() {
		return db;
	}
	
	public void setDb(String db) {
		this.db = db;
	}

}
