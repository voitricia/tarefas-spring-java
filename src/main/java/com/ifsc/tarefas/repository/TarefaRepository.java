package com.ifsc.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifsc.tarefas.model.Tarefa;

// Fala direto com o DB
// Com o JpaRepository cria o CRUD inicial
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {}

