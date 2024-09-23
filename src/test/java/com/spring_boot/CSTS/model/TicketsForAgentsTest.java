//package com.spring_boot.CSTS.model;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TicketsForAgentsTest {
//
//    @Test
//    public void testConstructorInitialization() {
//        // Given
//        Long id = 1L;
//        String title = "Sample Ticket";
//        Ticket.Status status = Ticket.Status.OPEN;
//        Ticket.Priority priority = Ticket.Priority.HIGH;
//
//        // When
//        TicketsForAgents ticketForAgent = new TicketsForAgents(id, title, status, priority);
//
//        // Then
//        assertEquals(id, ticketForAgent.getId(), "ID should be initialized correctly.");
//        assertEquals(title, ticketForAgent.getTitle(), "Title should be initialized correctly.");
//        assertEquals(status.name(), ticketForAgent.getStatus(), "Status should be initialized correctly.");
//        assertEquals(priority.name(), ticketForAgent.getPriority(), "Priority should be initialized correctly.");
//    }
//
//    @Test
//    public void testGettersAndSetters() {
//        // Given
//        TicketsForAgents ticketForAgent = new TicketsForAgents(null, null, null, null);
//
//        // When
//        ticketForAgent.setId(2L);
//        ticketForAgent.setTitle("Another Ticket");
//        ticketForAgent.setStatus(Ticket.Status.CLOSED.name());
//        ticketForAgent.setPriority(Ticket.Priority.MEDIUM.name());
//
//        // Then
//        assertEquals(2L, ticketForAgent.getId(), "ID should be set and retrieved correctly.");
//        assertEquals("Another Ticket", ticketForAgent.getTitle(), "Title should be set and retrieved correctly.");
//        assertEquals(Ticket.Status.CLOSED.name(), ticketForAgent.getStatus(), "Status should be set and retrieved correctly.");
//        assertEquals(Ticket.Priority.MEDIUM.name(), ticketForAgent.getPriority(), "Priority should be set and retrieved correctly.");
//    }
//
//    @Test
//    public void testConstructorWithNullValues() {
//        // Given
//        TicketsForAgents ticketForAgent = new TicketsForAgents(null, null, null, null);
//
//        // Then
//        assertNull(ticketForAgent.getId(), "ID should be null.");
//        assertNull(ticketForAgent.getTitle(), "Title should be null.");
//        assertNull(ticketForAgent.getStatus(), "Status should be null.");
//        assertNull(ticketForAgent.getPriority(), "Priority should be null.");
//    }
//
//    @Test
//    public void testSettersWithNullValues() {
//        // Given
//        TicketsForAgents ticketForAgent = new TicketsForAgents(1L, "Sample", Ticket.Status.OPEN, Ticket.Priority.LOW);
//
//        // When
//        ticketForAgent.setId(null);
//        ticketForAgent.setTitle(null);
//        ticketForAgent.setStatus(null);
//        ticketForAgent.setPriority(null);
//
//        // Then
//        assertNull(ticketForAgent.getId(), "ID should be null after setting it to null.");
//        assertNull(ticketForAgent.getTitle(), "Title should be null after setting it to null.");
//        assertNull(ticketForAgent.getStatus(), "Status should be null after setting it to null.");
//        assertNull(ticketForAgent.getPriority(), "Priority should be null after setting it to null.");
//    }
//
//    @Test
//    public void testStatusAndPriorityConversion() {
//        // Given
//        Ticket.Status status = Ticket.Status.IN_PROGRESS;
//        Ticket.Priority priority = Ticket.Priority.MEDIUM;
//
//        // When
//        TicketsForAgents ticketForAgent = new TicketsForAgents(1L, "Ticket", status, priority);
//
//        // Then
//        assertEquals(status.name(), ticketForAgent.getStatus(), "Status should be correctly converted to String.");
//        assertEquals(priority.name(), ticketForAgent.getPriority(), "Priority should be correctly converted to String.");
//    }
//
//    @Test
//    public void testPriorityConversion() {
//        // Given
//        Ticket.Priority priority = Ticket.Priority.HIGH;
//
//        // When
//        TicketsForAgents ticketForAgent = new TicketsForAgents(1L, "Ticket", Ticket.Status.OPEN, priority);
//
//        // Then
//        assertEquals(priority.name(), ticketForAgent.getPriority(), "Priority should be correctly converted to String.");
//    }
//}
