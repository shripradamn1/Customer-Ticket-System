package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(user, "User should be initialized.");
    }

    @Test
    public void testParameterizedConstructor() {
        // Given
        Integer id = 1;
        String username = "testUser";
        String email = "test@example.com";
        String role = "ROLE_USER";
        String password = "securePassword";

        // When
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(password);

        // Then
        assertEquals(id, user.getId(), "ID should be set correctly.");
        assertEquals(username, user.getUsername(), "Username should be set correctly.");
        assertEquals(email, user.getEmail(), "Email should be set correctly.");
        assertEquals(role, user.getRole(), "Role should be set correctly.");
        assertEquals(password, user.getPassword(), "Password should be set correctly.");
    }

    @Test
    public void testGetAuthorities() {
        // Given
        User user = new User();
        user.setRole("ROLE_USER");

        // When
        var authorities = user.getAuthorities();

        // Then
        assertNotNull(authorities, "Authorities should not be null.");
        assertEquals(1, authorities.size(), "There should be one authority.");
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")), "Authority should match.");
    }

    @Test
    public void testUserDetailsMethods() {
        // Given
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // When / Then
        assertEquals("testUser", user.getUsername(), "Username should be returned correctly.");
        assertEquals("testPassword", user.getPassword(), "Password should be returned correctly.");
        assertTrue(user.isAccountNonExpired(), "Account should be non-expired.");
        assertTrue(user.isAccountNonLocked(), "Account should be non-locked.");
        assertTrue(user.isCredentialsNonExpired(), "Credentials should be non-expired.");
        assertTrue(user.isEnabled(), "User should be enabled.");
    }

    // Add tests for other methods if necessary
}
