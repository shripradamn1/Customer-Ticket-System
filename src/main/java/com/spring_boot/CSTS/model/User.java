package com.spring_boot.CSTS.model;
import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", nullable = false, length = 50, unique = true)
	private String username;

	@Column(name = "Email", nullable = false, length = 100, unique = true)
	private String email;

	@Column(name = "role", nullable = false)
	private String role;

	@Column(name = "password")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

//	@Column(name = "CreatedAt", nullable = false, updatable = false)
//	@CreationTimestamp
//	private Timestamp createdAt;
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "category_id", nullable = false)  // Assuming every user needs to belong to a category
//	private Category category;
//
//
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="team_id")
//	private Team team;
//	@JsonManagedReference
//	@OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private Set<Ticket> assignedTickets;

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
	//@Override
//	public Set<Ticket> getAssignedTickets()
//	{
//		return assignedTickets;
//	}
//	//@Override
//	public Team getTeam() {
//		return team;
//	}
//	//@Override
//
//	public void setTeam(Team team) {
//		this.team = team;
//	}
//
//	public void setAssignedTickets(Set<Ticket> assignedTickets) {
//		this.assignedTickets = assignedTickets;
//	}


	@Override
	public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
		return java.util.Collections.singletonList(new SimpleGrantedAuthority(role));
	}

}