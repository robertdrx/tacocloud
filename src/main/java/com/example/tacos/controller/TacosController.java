package com.example.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TacosController {
	
	@GetMapping("/")
	public String getHomepage()
	{
		return "home";
	}
}
