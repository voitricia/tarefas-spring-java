package com.ifsc.tarefas.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsc.tarefas.model.Categoria;
import com.ifsc.tarefas.repository.CategoriaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/categorias") // Define padrão URL para o serviço de categorias

public class CategoriaServices {
    
    private final CategoriaRepository categoriaRepository;

    public CategoriaServices(CategoriaRepository categoriaRepository) { //quem tem a lógica, conecta os 2
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<?> buscarTodas() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }
    
    @PostMapping("/inserir")
    public ResponseEntity<?> criarNovaCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @RequestBody Categoria novaCategoria) {
        return categoriaRepository.findById(id).map(
            categoria -> {
                categoria.setNome(novaCategoria.getNome());
                return ResponseEntity.ok(categoriaRepository.save(categoria));
            }
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Categoria> deletarCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); 
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.ok().build(); 
    }

}
