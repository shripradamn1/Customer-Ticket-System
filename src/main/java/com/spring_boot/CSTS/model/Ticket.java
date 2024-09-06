package com.spring_boot.CSTS.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "assigned_to")
	private SupportAgent assignedTo;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	public void setStatus(Status status) {
	}
	public Team getTeam() {
		return new Team();
	}
	public enum Status {
		OPEN, IN_PROGRESS, ASSIGNED, CLOSED, RESOLVED
	}

	@Enumerated(EnumType.STRING)
	private Priority priority;

	public enum Priority {
		LOW, MEDIUM, HIGH
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SupportAgent getAssignedTo() {
		return assignedTo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}



	public void setTeam(Team team) {
		this.team = team;
	}
	public void setAssignedTo(SupportAgent assignedTo) {
		this.assignedTo = assignedTo;
	}
}
