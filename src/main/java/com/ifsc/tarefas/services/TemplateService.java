package com.ifsc.tarefas.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ifsc.tarefas.repository.TarefaRepository;



@Controller
@RequestMapping("/templates")
public class TemplateService {

        private final TarefaRepository tarefaRepository;
    
    public TemplateService (TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }
    
    @GetMapping("/listar")
    //model serve para adicionar atributos que serão usados no template (o html)
    String listarTarefas(Model model) {
        model.addAttribute("tarefas", tarefaRepository.findAll());
        
        return "lista";//vai dizer qual template vou usar
    }
    
}
