package com.ifsc.tarefas.services;
import com.ifsc.tarefas.model.Categoria;
import com.ifsc.tarefas.repository.CategoriaRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/templates/categorias")
public class TemplateCategoriaServices {

    private final CategoriaRepository categoriaRepository;

    public TemplateCategoriaServices (CategoriaServices categoriaServices, CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository; 
    }

    @GetMapping("/listar")
    String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "listar-categorias";
    }
    @GetMapping("/categoria")
    String novaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria";
    }
    @PostMapping("/salvar")
    String salvar(@ModelAttribute("categoria") Categoria categoria) { 
        categoriaRepository.save(categoria);
        return "redirect:/templates/categorias/listar"; 
    }
    @PostMapping("{id}/excluir")
    String excluir(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/templates/categorias/listar";
    }
    @GetMapping("editar/{id}")
    String editar(@PathVariable Long id, Model model) {
        var categoria = categoriaRepository.findById(id).orElse(null);
        if(categoria == null) {
            return "redirect:/templates/categorias/listar";
        }
        model.addAttribute("categoria", categoria);
        return "categoria";
    }
    
}
