package io.github.abhiraj.spendly.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.abhiraj.spendly.service.AppUserDetailsService;

@Configuration
public class SecurityConfig {

	private AppUserDetailsService appUserDetailsService;
	
	public SecurityConfig(AppUserDetailsService appUserDetailsService) {
		this.appUserDetailsService = appUserDetailsService;
	}
	
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
												.requestMatchers("/status","/health","/register","/activate", "/login").permitAll()
												.anyRequest().authenticated());
		
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(withDefaults());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("Authentication","Content-Type","Accept"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(appUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authenticationProvider);
	}
}
