package com.shaunwang.livekue.dto;

import javax.validation.constraints.*;

import com.shaunwang.livekue.model.ServiceType;

public class AddStudentRequest {
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String description;
	
	@NotNull
	private ServiceType serviceType;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	
	
}
