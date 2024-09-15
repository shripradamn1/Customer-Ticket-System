package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
    }


    @Test
    public void testDefaultValues() {
        // When
        Ticket ticket = new Ticket();

        // Then
        assertNull(ticket.getId(), "ID should be null by default.");
        assertNull(ticket.getTitle(), "Title should be null by default.");
        assertNull(ticket.getDescription(), "Description should be null by default.");
        assertNull(ticket.getCategory(), "Category should be null by default.");
        //assertNull(ticket.getTeam(), "Team should be null by default.");
        assertNull(ticket.getStatus(), "Status should be null by default.");
        assertNull(ticket.getUserId(), "UserId should be null by default.");
        assertNull(ticket.getAssignedTo(), "AssignedTo should be null by default.");
        assertNotNull(ticket.getCreatedAt(), "CreatedAt should be initialized.");
        //assertNotNull(ticket.getUpdatedAt(), "UpdatedAt should be initialized.");
    }

    @Test
    public void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String title = "Sample Ticket";
        String description = "Description of the ticket";
        Ticket.Status status = Ticket.Status.OPEN;
        Ticket.Priority priority = Ticket.Priority.HIGH;
        Long userId = 2L;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now().minusHours(1);
        Category category = new Category(); // Assuming Category is a valid class
        Team team = new Team(); // Assuming Team is a valid class
        SupportAgent assignedTo = new SupportAgent(); // Assuming SupportAgent is a valid class

        // When
        ticket.setId(id);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStatus(status);
        ticket.setPriority(priority);
        ticket.setUserId(userId);
        ticket.setCreatedAt(createdAt);
        //ticket.setUpdatedAt(updatedAt);
        ticket.setCategory(category);
        //ticket.setTeam(team);
        ticket.setAssignedTo(assignedTo);

        // Then
        assertEquals(id, ticket.getId(), "ID should be set and retrieved correctly.");
        assertEquals(title, ticket.getTitle(), "Title should be set and retrieved correctly.");
        assertEquals(description, ticket.getDescription(), "Description should be set and retrieved correctly.");
        assertEquals(status, ticket.getStatus(), "Status should be set and retrieved correctly.");
        assertEquals(priority, ticket.getPriority(), "Priority should be set and retrieved correctly.");
        assertEquals(userId, ticket.getUserId(), "UserId should be set and retrieved correctly.");
        assertEquals(createdAt, ticket.getCreatedAt(), "CreatedAt should be set and retrieved correctly.");
        //assertEquals(updatedAt, ticket.getUpdatedAt(), "UpdatedAt should be set and retrieved correctly.");
        assertEquals(category, ticket.getCategory(), "Category should be set and retrieved correctly.");
        //assertEquals(team, ticket.getTeam(), "Team should be set and retrieved correctly.");
        assertEquals(assignedTo, ticket.getAssignedTo(), "AssignedTo should be set and retrieved correctly.");
    }

    @Test
    public void testEnumHandling() {
        // Given
        Ticket.Status status = Ticket.Status.IN_PROGRESS;
        Ticket.Priority priority = Ticket.Priority.MEDIUM;

        // When
        ticket.setStatus(status);
        ticket.setPriority(priority);

        // Then
        assertEquals(status, ticket.getStatus(), "Status enum should be set and retrieved correctly.");
        assertEquals(priority, ticket.getPriority(), "Priority enum should be set and retrieved correctly.");
    }
}
