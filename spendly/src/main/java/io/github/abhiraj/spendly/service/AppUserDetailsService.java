package io.github.abhiraj.spendly.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.abhiraj.spendly.entity.ProfileEntity;
import io.github.abhiraj.spendly.repository.ProfileRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	private ProfileRepository profileRepository;
	
	public AppUserDetailsService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		ProfileEntity existingProfile = profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Profile with emailId: "+email+" does not exists"));
		return User.builder()
					.username(existingProfile.getEmail())
					.password(existingProfile.getPassword()) 
					.build();
	}

}
