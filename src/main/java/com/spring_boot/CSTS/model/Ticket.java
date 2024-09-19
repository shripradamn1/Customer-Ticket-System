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
	@Column(name = "attachment")
	private String attachment;

	public String getAttachment() {
    return attachment;
	}

	public void setAttachment(String attachment) {
    this.attachment = attachment;
	}

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@Enumerated(EnumType.STRING)
	private Status status;

	@JoinColumn(name = "created_by")
	private Long userId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "assigned_to")
	private SupportAgent assignedTo;

//	@JsonBackReference
//	@ManyToOne
//	@JoinColumn(name = "assigned_to")
//	private User assignedToUser;


	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Team getTeam() {
		return new Team();
	}
	public enum Status {
		OPEN, IN_PROGRESS, ASSIGNED, CLOSED, RESOLVED
	}
	public void setStatus(Status status) {
		this.status=status;
	}

	@Enumerated(EnumType.STRING)
	private Priority priority;

	public enum Priority {
		LOW, MEDIUM, HIGH}
	public void setPriority(Priority priority){
		this.priority=priority;
	}

	public void setTitle(String title) {
		this.title =
				title;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public SupportAgent getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(SupportAgent assignedTo) {
		this.assignedTo = assignedTo;
	}

		public void setTeam(Team team) {
		this.team = team;
	}


	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public Priority getPriority() {
		return priority;
	}
}
