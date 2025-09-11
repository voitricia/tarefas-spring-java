package com.ifsc.tarefas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @Column(name = "username", nullable = false)
    @NotBlank(message = "O campo username é obrigatório")
    @Size(min = 3, max = 100, message = "O campo username deve ter entre 3 e 100 caracteres")
    private String username;
    
    @Column(name = "password", nullable = false)
    @NotNull(message = "O campo password é obrigatório")
    private String password;

    @Column(name = "role", nullable = false)
    @NotNull(message = "O campo role é obrigatório")
    private String role;//papel do usuario

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
