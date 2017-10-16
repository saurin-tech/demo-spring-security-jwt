package com.example.demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping(value="/public")
	public String publicHello() {
		return "Public Hello!";
	}
	
	@RequestMapping(value="/private")
	public String privateHello() {
		return "Private Hello!";
	}
}
