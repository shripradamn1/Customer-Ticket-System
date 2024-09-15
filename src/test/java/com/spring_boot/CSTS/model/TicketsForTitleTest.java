package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TicketsForTitleTest {

    private TicketsForTitle ticket;

    @BeforeEach
    public void setUp() {
        ticket = new TicketsForTitle();
    }

    @Test
    public void testDefaultValues() {
        // When
        TicketsForTitle ticket = new TicketsForTitle();

        // Then
        assertNull(ticket.getId(), "ID should be null by default.");
        assertNull(ticket.getTitle(), "Title should be null by default.");
        assertNull(ticket.getDescription(), "Description should be null by default.");
        assertNull(ticket.getStatus(), "Status should be null by default.");
        assertNotNull(ticket.getCreatedAt(), "CreatedAt should be initialized.");
    }

    @Test
    public void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String title = "Sample Ticket";
        String description = "Description of the ticket";
        Ticket.Status status = Ticket.Status.OPEN;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);

        // When
        ticket.setId(id);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStatus(status);
        ticket.setCreatedAt(createdAt);

        // Then
        assertEquals(id, ticket.getId(), "ID should be set and retrieved correctly.");
        assertEquals(title, ticket.getTitle(), "Title should be set and retrieved correctly.");
        assertEquals(description, ticket.getDescription(), "Description should be set and retrieved correctly.");
        assertEquals(status, ticket.getStatus(), "Status should be set and retrieved correctly.");
        assertEquals(createdAt, ticket.getCreatedAt(), "CreatedAt should be set and retrieved correctly.");
    }

    @Test
    public void testEnumStatus() {
        // Given
        Ticket.Status statusOpen = Ticket.Status.OPEN;
        Ticket.Status statusInProgress = Ticket.Status.IN_PROGRESS;

        // When
        ticket.setStatus(statusOpen);
        Ticket.Status retrievedStatus = ticket.getStatus();

        // Then
        assertEquals(statusOpen, retrievedStatus, "Status should be set and retrieved correctly.");

        // When
        ticket.setStatus(statusInProgress);
        retrievedStatus = ticket.getStatus();

        // Then
        assertEquals(statusInProgress, retrievedStatus, "Status should be updated and retrieved correctly.");
    }
    @Test
    public void testConstructor() {
        // Given
        Long id = 1L;
        String title = "Sample Ticket";
        Ticket.Status status = Ticket.Status.OPEN;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);

        // When
        TicketsForTitle ticket = new TicketsForTitle(id, title, status, createdAt);

        // Then
        assertEquals(id, ticket.getId(), "ID should be set correctly by the constructor.");
        assertEquals(title, ticket.getTitle(), "Title should be set correctly by the constructor.");
        assertEquals(status, ticket.getStatus(), "Status should be set correctly by the constructor.");
        assertEquals(createdAt, ticket.getCreatedAt(), "CreatedAt should be set correctly by the constructor.");
    }

//    @Test
//    public void testEnumPriority() {
//        // Given
//        Ticket.Priority priorityLow = Ticket.Priority.LOW;
//        Ticket.Priority priorityHigh = Ticket.Priority.HIGH;
//
//        // When
//        ticket.setPriority(priorityLow);
//        Ticket.Priority retrievedPriority = ticket.getPriority();
//
//        // Then
//        assertEquals(priorityLow, retrievedPriority, "Priority should be set and retrieved correctly.");
//
//        // When
//        ticket.setPriority(priorityHigh);
//        retrievedPriority = ticket.getPriority();
//
//        // Then
//        assertEquals(priorityHigh, retrievedPriority, "Priority should be updated and retrieved correctly.");
//    }
}
