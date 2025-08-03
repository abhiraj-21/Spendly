package io.github.abhiraj.spendly.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.abhiraj.spendly.dto.AuthDTO;
import io.github.abhiraj.spendly.dto.ProfileDTO;
import io.github.abhiraj.spendly.service.ProfileService;

@RestController
public class ProfileController {

	private ProfileService profileService;
	
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDto) {
		ProfileDTO registeredProfile = profileService.registerProfile(profileDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
	}
	
	@GetMapping("/activate")
	public ResponseEntity<String> activateProfile(@RequestParam String tokens) {
		boolean isActivated = profileService.activateProfile(tokens);
		if(isActivated) return ResponseEntity.ok("Profile activated Successfully");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation Token not found or already used");
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDto){
		try {
			if(!profileService.isAccountActive(authDto.getEmail())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message","Please Activate your account first!!"));
			}
			Map<String, Object> response = profileService.authenticateAndGenerateToken(authDto);
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
		}
	}
	
}
