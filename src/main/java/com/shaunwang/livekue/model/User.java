package com.shaunwang.livekue.model;

import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "studentNumber"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String studentName;

    @NotBlank
    @Size(max = 8)
    private String studentNumber;


	@NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;
    
    @NotNull
    private Role role;
    
    @NotBlank
    @Size(max=4)
    private String displayName;
    
    private long lineId;


    public long getLineId() {
		return lineId;
	}

	public void setLineId(long lineId) {
		this.lineId = lineId;
	}

	public User() {

    }

    public User(String studentName, String displayName, String studentNumber, String email, String password) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }

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
    
    public Role getRole() {
    	return role;
    }
    
    public void setRole(Role role) {
		this.role = role;
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
	
	
    
    

}