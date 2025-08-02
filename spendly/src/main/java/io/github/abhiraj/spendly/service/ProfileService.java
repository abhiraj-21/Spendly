package io.github.abhiraj.spendly.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.abhiraj.spendly.dto.ProfileDto;
import io.github.abhiraj.spendly.entity.ProfileEntity;
import io.github.abhiraj.spendly.repository.ProfileRepository;

@Service
public class ProfileService {

	private ProfileRepository profileRepository;
	private EmailService emailService;
	
	public ProfileService(ProfileRepository profileRepository, EmailService emailService) {
		this.profileRepository = profileRepository;
		this.emailService = emailService;
	}
	
	public ProfileDto registerProfile(ProfileDto profileDto) {
		ProfileEntity newProfile = new ProfileEntity(profileDto);
		newProfile.setActivationToken(UUID.randomUUID().toString());
		newProfile = profileRepository.save(newProfile);
		
		//Send activation link: 1. create activationLink
		String activationLink = "http://localhost:8080/api/v1.0/activation?token="+newProfile.getActivationToken();
		
		//2. Prepare the mail subject and body
		String emailSubject = "Thank You For Registering in Spendly!! Activate your account now";
		String emailBody = "Click on the following link to activate your account: "+activationLink ;
		
		//3. Send Email using Email Service
		emailService.sendEmail(profileDto.getEmail(), emailSubject, emailBody );
		
		return new ProfileDto(newProfile);
	}
	
}
