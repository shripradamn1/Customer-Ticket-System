package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.UserRepository;
import com.spring_boot.CSTS.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean hasRole(User user, String roleName) {
        return user.getRole().getRoleName().equalsIgnoreCase(roleName);
    }
}
