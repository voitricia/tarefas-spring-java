package com.ifsc.tarefas.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "categoria_id")
    private Long id;
    private String nome;

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias") //mapeado pela lista categorias na classe Tarefa
    private Set<Tarefa> tarefas = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public Set<Tarefa> getTarefas() {
        return tarefas;
    }
    public void setTarefas(Set<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
