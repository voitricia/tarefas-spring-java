package com.ifsc.tarefas.auth;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.ifsc.tarefas.model.User;
import com.ifsc.tarefas.repository.UserRepository;

@Repository
public class AuthRepository {
    private final Map<String, SessionInfo> tokenStore = new ConcurrentHashMap<>();

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthRepository(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public Optional<String> login(String usarname, String password) {
        return userRepository.findById(usarname)
            .filter(u -> u.getPassword().equals(password))
            .map(u -> {
                String jwt = jwtUtil.generateToken(u.getUsername(), u.getRole()); // gera o token
                tokenStore.put(jwt, new SessionInfo(u.getUsername(), Instant.now()));
                return jwt;
            });
    }

    public Optional<String> validate(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        try {
            SessionInfo info = tokenStore.get(token);
            if (info == null) return Optional.empty();
            return Optional.ofNullable(jwtUtil.getSubject(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void logout(String token) {
        if (token != null ){
        tokenStore.remove(token);
        }
    }

    public boolean register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        if (userRepository.existsByUsername(username)) {
            return false;            
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setRole("USER");

        userRepository.save(u);
        return true;
    }

    public String getRoleByUsername(String username) {
        return userRepository.findByUsername(username).map(User::getRole).orElse("USER");
    }

    private record SessionInfo(String username, Instant authenticatedAt) {}
    
}
