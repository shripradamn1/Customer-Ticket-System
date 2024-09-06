package com.spring_boot.CSTS.Service;

import java.util.Optional;

import com.spring_boot.CSTS.Repository.UserRepository;
import com.spring_boot.CSTS.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthenticationServiceImpl {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_AGENT = "ROLE_AGENT";
    public static final String ROLE_ADMIN= "ROLE_ADMIN";

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void  signupUser(User user) throws Exception {
        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Email or username is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(ROLE_USER);
        userRepository.save(user);
    }
    public void signupAgent(User agent) throws Exception {
        if(userRepository.findByEmail(agent.getEmail()).isPresent() || userRepository.findByUsername(agent.getUsername()).isPresent()) {
            throw new Exception("Email or username is already taken");
        }
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        agent.setRole(ROLE_AGENT);
        userRepository.save(agent);
    }
    public void signupAdmin(User admin) throws Exception {
        if(userRepository.findByEmail(admin.getEmail()).isPresent() || userRepository.findByUsername(admin.getUsername()).isPresent()) {
            throw new Exception("Email or username is already taken");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(ROLE_ADMIN);
        userRepository.save(admin);
    }



    public UserDetails getCurrentLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails;
            }
        }
        return null;
    }

    public User getCurrentUserDetails() {
        UserDetails userDetails = getCurrentLoggedInUserDetails();
        Optional<User> optUser = userRepository.findByUsername(userDetails.getUsername());
        return optUser.get();
    }

    public User getUserDetailsByUserId(Integer userId) {
        Optional<User> optUser = userRepository.findById(Long.valueOf(userId));
        return optUser.get();
    }
}