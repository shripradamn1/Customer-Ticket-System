package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.AuthenticationServiceImpl;
import com.spring_boot.CSTS.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin
// @RequestMapping("/auth")
public class    AuthController {

    //Sign up
    //Login
    //Details of a user

    @Autowired
    public PasswordEncoder passwordEncoder;


    @Autowired
    public AuthenticationServiceImpl authService;

    @PostMapping("/signup/user")
    public ResponseEntity<String> signupUser(@RequestBody User user) throws Exception {
        authService.signupUser(user);
        return new ResponseEntity<String>("User succefully created", HttpStatus.CREATED);
    }
    @PostMapping("/signup/agent")
    public ResponseEntity<String> signupAgent(@RequestBody User agent) throws Exception {
        System.out.println(agent);
        authService.signupAgent(agent);
        return new ResponseEntity<String>("agent succefully created", HttpStatus.CREATED);
    }
    @PostMapping("/signup/admin")
    public ResponseEntity<String> signupAdmin(@RequestBody User admin) throws Exception {
        authService.signupAdmin(admin);
        return new ResponseEntity<String>("admin succefully created", HttpStatus.CREATED);
    }


    @GetMapping("/checkLoggedInUser")
    public ResponseEntity<User> getUserDetails() {
        return new ResponseEntity<User>(authService.getCurrentUserDetails(), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }


    @GetMapping("/home")
    public String home() {
        return "successfully logged in !!";
    }
}