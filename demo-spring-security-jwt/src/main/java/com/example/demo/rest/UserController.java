package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ApplicationUser;
import com.example.demo.repo.ApplicationUserRepo;

@RestController
@RequestMapping("/users")
public class UserController {

	private ApplicationUserRepo appUserRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserController(ApplicationUserRepo appUserRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepo = appUserRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public void signUp(@RequestBody ApplicationUser user) {
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		appUserRepo.save(user);
	}
}
