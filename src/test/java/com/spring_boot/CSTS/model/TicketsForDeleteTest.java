package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class TicketsForDeleteTest {

    @Test
    public void testConstructorInitialization() throws NoSuchFieldException, IllegalAccessException {
        // Given
        Long id = 1L;
        String title = "Sample Ticket";

        // When
        TicketsForDelete ticket = new TicketsForDelete(id, title);

        // Then
        Field idField = TicketsForDelete.class.getDeclaredField("id");
        idField.setAccessible(true);
        assertEquals(id, idField.get(ticket), "ID should be initialized correctly.");

        Field titleField = TicketsForDelete.class.getDeclaredField("title");
        titleField.setAccessible(true);
        assertEquals(title, titleField.get(ticket), "Title should be initialized correctly.");
    }

    @Test
    public void testNoArgsConstructor() throws NoSuchFieldException, IllegalAccessException {
        // When
        TicketsForDelete ticket = new TicketsForDelete();

        // Then
        Field idField = TicketsForDelete.class.getDeclaredField("id");
        idField.setAccessible(true);
        assertNull(idField.get(ticket), "ID should be null for no-args constructor.");

        Field titleField = TicketsForDelete.class.getDeclaredField("title");
        titleField.setAccessible(true);
        assertNull(titleField.get(ticket), "Title should be null for no-args constructor.");
    }
}
