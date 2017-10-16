package com.example.demo.model;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repo.ApplicationUserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private ApplicationUserRepo appUserRepo;
	
	@Autowired
	public UserDetailsServiceImpl(ApplicationUserRepo appUserRepo) {
		this.appUserRepo = appUserRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser appUser = appUserRepo.findByUsername(username);
		
		if(appUser == null) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}
		return new User(appUser.getUsername(), appUser.getPassword(), emptyList());
	}
}
