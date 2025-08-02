package io.github.abhiraj.spendly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/health")
	public String healthCheck() {
		return "Application is running!!";
	}
	
}
