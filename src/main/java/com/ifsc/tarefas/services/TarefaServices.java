// comunica com o front
package com.ifsc.tarefas.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController // anotação que indica que essa classe é um service
@RequestMapping("/tarefas") // anotação que define padrão url 
public class TarefaServices {
    
    // Injetando o repositório de tarefa pra usar no service e buscar coisas do db
    private final TarefaRepository tarefaRepository;
    
    public TarefaServices(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    //anotação pra get - busca todas as tarefas
    @GetMapping("/buscar-todos")
    public ResponseEntity<?> buscarTodas(){
        return ResponseEntity.ok(tarefaRepository.findAll());
    }
    
    //cria tarefa
    @PostMapping("/inserir")
    public ResponseEntity<Tarefa> criarNovaTarefa(@RequestBody Tarefa tarefa){
        return ResponseEntity.ok(tarefaRepository.save(tarefa));
    }

    //editar tarefa
    @PutMapping("editar/{id}")
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Long id, @RequestBody Tarefa novaTarefa) {
        return tarefaRepository.findById(id).map(
        // Busca a tarefa pelo ID no DB e atualiza os campos/atributos necessários
            tarefa -> {
                tarefa.setTitulo(novaTarefa.getTitulo());
                tarefa.setDescricao(novaTarefa.getDescricao());
                tarefa.setResponsavel(novaTarefa.getResponsavel());
                tarefa.setDataLimite(novaTarefa.getDataLimite());
                tarefa.setStatus(novaTarefa.getStatus());
                tarefa.setPrioridade(novaTarefa.getPrioridade());
                return ResponseEntity.ok(tarefaRepository.save(tarefa));
            }
        ).orElse(ResponseEntity.notFound().build());//se não encontrar a tarefa, retorna null
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Tarefa> deletarTarefa(@PathVariable Long id) {
        if (!tarefaRepository.existsById(id)){//verifica se a tarefa existe no db - !:negação
            return ResponseEntity.notFound().build();
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
