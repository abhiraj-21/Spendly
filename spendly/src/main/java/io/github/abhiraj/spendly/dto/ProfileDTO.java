package io.github.abhiraj.spendly.dto;

import java.time.LocalDateTime;

import io.github.abhiraj.spendly.entity.ProfileEntity;
import lombok.Data;

@Data
public class ProfileDTO {

	private Long id;
	private String fullName;
	private String email;
	private String password;
	private String profileImageUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public ProfileDTO() {}

	public ProfileDTO(Long id, String fullName, String email, String password, String profileImageUrl,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.profileImageUrl = profileImageUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ProfileDTO(ProfileEntity profile) {
		this.id = profile.getId();
		this.fullName = profile.getFullName();
		this.email = profile.getEmail();
		this.profileImageUrl = profile.getProfileImageUrl();
		this.createdAt = profile.getCreatedAt();
		this.updatedAt = profile.getUpdatedAt();
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

	@Override
	public String toString() {
		return "ProfileDto [id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", profileImageUrl=" + profileImageUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
	
	
	
}
