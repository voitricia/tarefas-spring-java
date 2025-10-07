package com.ifsc.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifsc.tarefas.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}