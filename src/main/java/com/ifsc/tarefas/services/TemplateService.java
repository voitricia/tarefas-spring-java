package com.ifsc.tarefas.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsc.tarefas.model.Prioridade;
import com.ifsc.tarefas.model.Status;
import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/templates")
public class TemplateService {

        private final TarefaRepository tarefaRepository;
    
    public TemplateService (TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }
    
    @GetMapping("/listar")//página para listar as tarefas
    //model serve para adicionar atributos que serão usados no template (o html)
    String listarTarefas(Model model) {
        model.addAttribute("tarefas", tarefaRepository.findAll());
        return "listar";//vai dizer qual template vou usar
    }

    @GetMapping("/tarefa") //página para criar uma nova tarefa
    String novaTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("listaStatus", Status.values());
        return "tarefa";
    }
    
    @PostMapping("/salvar")//api que vai receber os dados do formulário
    String salvar(@ModelAttribute("tarefa") Tarefa tarefa) { // @ModelAttribute indica que o objeto tarefa será preenchido com os dados do formulário
        tarefaRepository.save(tarefa);
        return "redirect:/templates/listar"; //redireciona para a página de listagem após salvar
    }

    @PostMapping("{id}/excluir")
    String excluir(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
        return "redirect:/templates/listar";
    }

    @GetMapping("{id}/editar")
    String editar(@PathVariable Long id, Model model) {
        // vai procurar tarefas pelo id, se n achar
        var tarefa = tarefaRepository.findById(id).orElse(null);
        if(tarefa == null) {
            // retornar para pagina inicial
            return "redirect:/templates/listar";
        }
        // adiciona a tarefa que vai ser editada e todo o resto ao template do formulario
        model.addAttribute("tarefa", tarefa);
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("listaStatus", Status.values());
        return "tarefa";
    }
    
}
