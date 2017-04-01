package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/hello")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello() {
		return "sayHello";
	}

}
