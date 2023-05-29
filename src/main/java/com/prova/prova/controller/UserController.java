package com.prova.prova.controller;

import com.prova.prova.model.User;
import com.prova.prova.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        User user = userRepository.findByUsername(username);
        if (user != null && verifyPassword(password,user.getPassword())) {
            // password is correct
            // create new session if session is not new

            if (!session.isNew()) {
                session.invalidate();
            }
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());

            return user.getRole()+"/"+user.getUsername();
        } else {
            // password is incorrect or user does not exist
            return "error"+"/"+"error";
        }
    }
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available.");
        }
    }

    private static boolean verifyPassword(String inputPassword, String hashedPassword) {
        return hashPassword(inputPassword).equals(hashedPassword);
    }

}
