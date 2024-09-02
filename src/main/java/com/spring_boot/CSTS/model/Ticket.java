package com.spring_boot.CSTS.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
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

	@ManyToOne
	@JoinColumn(name = "assigned_to")
	private User assignedTo;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	public enum Status {
		OPEN, IN_PROGRESS, ASSIGNED, CLOSED, RESOLVED
	}

	@Enumerated(EnumType.STRING)
	private Priority priority;

	public enum Priority {
		LOW, MEDIUM, HIGH
	}

	// Getters and Setters
	// existing getters and setters...

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
