package com.spring_boot.CSTS.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.spring_boot.CSTS.model.User;
import com.spring_boot.CSTS.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        // Given
        String username = "testUser";
        User user = new User();
//        user.setUsername(username);
//        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
       // assertEquals(username, userDetails.getUsername());
       // verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Given
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(username);
        });
        verify(userRepository, times(1)).findByUsername(username);
    }

}
