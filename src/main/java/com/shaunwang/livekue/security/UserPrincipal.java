package com.shaunwang.livekue.security;

import com.shaunwang.livekue.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class UserPrincipal implements UserDetails {
    private Long id;

    private String studentName;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private GrantedAuthority authority;

    public UserPrincipal(Long id, String studentName, String studentNumber, String email, String password, GrantedAuthority authority) {
        this.id = id;
        this.studentName = studentName;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(this.authority);
    }

    public static UserPrincipal create(User user) {
        GrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole().toString());

        return new UserPrincipal(
                user.getId(),
                user.getStudentName(),
                user.getStudentNumber(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }
    
    @Override
    public String getUsername() {
        return email;
    }

    public void setStudentNumber(int studentNumber) {
	}

	@Override
    public String getPassword() {
        return password;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
