package io.github.abhiraj.spendly.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.abhiraj.spendly.dto.AuthDTO;
import io.github.abhiraj.spendly.dto.ProfileDTO;
import io.github.abhiraj.spendly.entity.ProfileEntity;
import io.github.abhiraj.spendly.repository.ProfileRepository;

@Service
public class ProfileService {

	private ProfileRepository profileRepository;
	private EmailService emailService;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	
	public ProfileService(ProfileRepository profileRepository, EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.profileRepository = profileRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	public ProfileDTO registerProfile(ProfileDTO profileDto) {
		ProfileEntity newProfile = new ProfileEntity(profileDto);
		newProfile.setActivationToken(UUID.randomUUID().toString());
		newProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
		newProfile = profileRepository.save(newProfile);
		
		//Send activation link: 1. create activationLink
		String activationLink = "http://localhost:8080/api/v1.0/activate?tokens="+newProfile.getActivationToken();
		
		//2. Prepare the mail subject and body
		String emailSubject = "Thank You For Registering in Spendly!! Activate your account now";
		String emailBody = "Click on the following link to activate your account: "+activationLink ;
		
		//3. Send Email using Email Service
		emailService.sendEmail(profileDto.getEmail(), emailSubject, emailBody );
		
		return new ProfileDTO(newProfile);
	}
	
	public boolean activateProfile(String token) {
		ProfileEntity profile = profileRepository.findByActivationToken(token).orElse(null);
		if(profile == null) return false;
		profile.setIsActive(true);
		profileRepository.save(profile);
		return true;
	}
	
	public boolean isAccountActive(String email) {
		ProfileEntity profile = profileRepository.findByEmail(email).orElse(null);
		if(profile == null) return false;
		return profile.getIsActive();
	}
	
	public ProfileEntity getCurrentProfile() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 return profileRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+authentication.getName()));
	}
	
	public ProfileDTO getPublicProfile(String email) {
		ProfileEntity currentProfile = null;
		if(email == null) {
			currentProfile = getCurrentProfile();
		}else {
			currentProfile = profileRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Profile not found with email: "+email));
		}
		return new ProfileDTO(currentProfile);
	}

	public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDto) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
			return Map.of(
						"token","JWT Token",
						"user",getPublicProfile(authDto.getEmail())
					);
		}catch(Exception e) {
			throw new RuntimeException("Invalid Credentials");
		}
	}
	
}
