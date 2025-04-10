package com.expenseTracker.auth.service;

import com.expenseTracker.auth.model.User;
import com.expenseTracker.auth.model.Role;
import com.expenseTracker.auth.repository.UserRepository;
import com.expenseTracker.auth.util.JwtUtil;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<String> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user);
                logger.info("✅ Token generated for user: {}", username);
                return Optional.of(token);
            } else {
                logger.warn("❌ Incorrect password for user: {}", username);
            }
        } else {
            logger.warn("❌ User not found: {}", username);
        }

        return Optional.empty();
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        logger.info("New USER registered: {}", savedUser.getUsername());
        return savedUser;
    }

    public User registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        User savedAdmin = userRepository.save(user);
        logger.info("New ADMIN registered: {}", savedAdmin.getUsername());
        return savedAdmin;
    }
}
