package com.ifsc.tarefas.auth;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "redirect", required = false) String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "cadastro";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam(name = "redirect", required = false) String redirect, Model model,
                          HttpServletResponse response) {

        Optional<String> token = authRepository.login(username, password);
        if (token.isEmpty()) {
            model.addAttribute("error", "Login ou senha incorretos");
            model.addAttribute("redirect", redirect);
            return "login";
        }

        String tokenValue = token.get();

        Cookie cookie = new Cookie("AUTH_TOKEN", tokenValue);

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        String target = (redirect == null || redirect.isBlank()) ? "/templates/listar" : redirect;

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
                         HttpServletResponse response) {

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

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (username == null || username.isBlank() || password == null || password.isBlank() || confirmPassword == null
                || confirmPassword.isBlank()) {
            model.addAttribute("erro", "Todos os campos devem ser preenchidos");
            return "cadastro";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "As senhas devem ser iguais");
            return "cadastro";
        }

        boolean criouComSucesso = authRepository.register(username, password);
        if (criouComSucesso) {
            return "login";
        } else {
            model.addAttribute("error", "Ocorreu um erro ao criar o usuario");
            return "cadastro";
        }
    }

}
