package com.ifsc.tarefas.auth;

import java.util.Optional;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @GetMapping("/login")
    public String loginPage (@RequestParam(name = "redirect", required = false) String redirect, Model model){
        model.addAttribute("redirect", redirect);
        return "login";
    }    

    @GetMapping("/register")
    public String registerPage (){
        return "cadastro";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam(name = "redirect", required = false) String redirect,
                          Model model,
                          HttpServletRequest request) {

        Optional<String> maybeToken = authService.login(username, password);
        if (maybeToken.isEmpty()) {
            model.addAttribute("error", "Usuário ou senha inválidos");
            model.addAttribute("redirect", redirect);
            return "login";
        }

        String token = maybeToken.get();

        Cookie cookie = new Cookie("AUTH_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        String target = (redirect != null || redirect.isBlank()) ? "/templates" : redirect;
        if (target.contains("://")) {
            target = "/templates";
        }
        if (!target.startsWith("/")) {
            target = "/" + target;
        }
        return "redirect:" + target;
    }

    @PostMapping("/logout")
    public String logout(@RequestParam(name = "redirect", required = false) String redirect, 
                         @RequestParam(name = "token", required = false) String tokenFromForm,
                         HttpServletRequest request) {

        if (tokenFromForm != null && !tokenFromForm.isBlank()) {
            authRepository.logout(tokenFromForm);
        }

        Cookie cookie = new Cookie("AUTH_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira o cookie imediatamente
        response.addCookie(cookie);

        String target = (redirect != null && !redirect.isBlank()) ? "/login" : redirect;
        return "redirect:" + target;
        }
    
}
