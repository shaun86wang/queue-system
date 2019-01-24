package com.shaunwang.livekue.dto;

import javax.validation.constraints.*;

public class SignUpRequest {
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	
	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}


	@NotBlank
    @Size(max = 40)
    private String firstName;
    
    @NotBlank
    @Size(max = 40)
    private String lastName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 8)
    private String studentNumber;


	@NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
