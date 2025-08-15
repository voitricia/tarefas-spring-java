package com.ifsc.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifsc.tarefas.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
