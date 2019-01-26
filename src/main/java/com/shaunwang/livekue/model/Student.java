package com.shaunwang.livekue.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.joda.time.DateTime;

@Entity
public class Student extends DateAudit {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@NotBlank
	private String studentName;
	
	@NotBlank
	@Size(max=8)
	private String studentNumber;
	
	@NotBlank
	@Size(max=4)
	private String displayName;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Size(max=500)
	private String description;
	
	@Past
	private DateTime inlineDateTime;
	
	
	@Past
	private DateTime startTime;
	
	@Past
	private DateTime endTime;
	
	private int totalTime;
	

	
	@NotNull
	private Status status;
	
	@NotNull
	private ServiceType serviceType;
	
	private String stationComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

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

	

	public DateTime getInlineDateTime() {
		return inlineDateTime;
	}

	public void setInlineDateTime(DateTime inlineDateTime) {
		this.inlineDateTime = inlineDateTime;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getStationComment() {
		return stationComment;
	}

	public void setStationComment(String stationComment) {
		this.stationComment = stationComment;
	}

	
	
	
	
	
	
	
	
	

	
}
