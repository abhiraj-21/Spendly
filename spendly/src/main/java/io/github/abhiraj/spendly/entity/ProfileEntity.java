package io.github.abhiraj.spendly.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.github.abhiraj.spendly.dto.ProfileDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_profiles")
public class ProfileEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	private String fullName;
	@Column(unique=true)
	private String email;
	private String password;
	private String profileImageUrl;
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	private Boolean isActive;
	private String activationToken;
	
	@PrePersist
	public void prePersist() {
		if(isActive == null) {
			isActive = false;
		}
	}
	
	ProfileEntity(){}

	public ProfileEntity(Long id, String fullName, String email, String password, String profileImageUrl,
			LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive, String activationToken) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.profileImageUrl = profileImageUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.activationToken = activationToken;
	}
	
	public ProfileEntity(ProfileDTO profileDto) {
		this.id = profileDto.getId();
		this.fullName = profileDto.getFullName();
		this.email = profileDto.getEmail();
		this.password = profileDto.getPassword();
		this.profileImageUrl = profileDto.getProfileImageUrl();
		this.createdAt = profileDto.getCreatedAt();
		this.updatedAt = profileDto.getUpdatedAt();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	@Override
	public String toString() {
		return "ProfileEntity [id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", profileImageUrl=" + profileImageUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", isActive=" + isActive + ", activationToken=" + activationToken + "]";
	}

	
	
}
