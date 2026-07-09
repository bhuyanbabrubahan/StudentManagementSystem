package com.sms.security.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sms.entity.BaseEntity;
import com.sms.entity.Faculty;
import com.sms.entity.Student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserStatus status;

	/*
	 * Inverse side of relationship.
	 *
	 * Student table owns relationship because Student contains user_id foreign key.
	 */
	@OneToOne(mappedBy = "user",
			fetch = FetchType.LAZY)
	@ToString.Exclude
	private Student student;
	
	
	@OneToOne(
	        mappedBy = "user",
	        fetch = FetchType.LAZY
	)
	@ToString.Exclude
	private Faculty faculty;

	/*
	 * Spring Security authority mapping
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));

	}

	/*
	 * Spring Security uses this as username.
	 *
	 * Here email is login identity.
	 */
	@Override
	public String getUsername() {

		return email;

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

		return status == UserStatus.ACTIVE;

	}

}