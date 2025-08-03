package io.github.abhiraj.spendly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.abhiraj.spendly.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{
	
	Optional<ProfileEntity> findByEmail(String email);
	
	Optional<ProfileEntity> findByActivationToken(String activationToken);
	
}
