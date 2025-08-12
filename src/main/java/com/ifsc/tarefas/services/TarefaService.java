// comunica com o front
package com.ifsc.tarefas.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController // anotação que indica que essa classe é um service
@RequestMapping("/tarefas") // anotação que define padrão url 
public class TarefaService {
    
    // Injetando o repositório de tarefa pra usar no service e buscar coisas do db
    private final TarefaRepository tarefaRepository;
    public TarefaService (TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    //anotação pra get - busca todas as tarefas
    @GetMapping("/buscar-todos")
    public ResponseEntity<?> buscarTodas(){
        return ResponseEntity.ok(tarefaRepository.findAll());
    }
    
    //cria tarefa
    @PostMapping("/inserir")
    public ResponseEntity<Tarefa> criarNovaTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.ok(tarefaRepository.save(tarefa));
    }
    
}
