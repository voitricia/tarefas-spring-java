package com.ifsc.tarefas.auth;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{

    private final AuthService authService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final JwtUtil jwtUtil;

    private static final Set<String> PUBLIC_PATTERNS = Set.of(
        "/login",
        "/login/**",
        "/register",
        "/register/**",
        "/style.css",
        "/js/**",
        "/images/**",
        "/webjars/**",
        "/h2-console/**",
        "/",
        "/error"
    );
    
    public AuthFilter(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        for(String pattern : PUBLIC_PATTERNS){
            if(pathMatcher.match(pattern, path)){
                return true;
            }
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = exctractTokenFromCookie(request, "AUTH_TOKEN");

        if (token == null || token.isBlank()){
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }
    }

    private String exctractTokenFromCookie(HttpServletRequest request, String cookieAutenticacao) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                return cookie.getValue();
            }
        }

        return null;
    }
}
