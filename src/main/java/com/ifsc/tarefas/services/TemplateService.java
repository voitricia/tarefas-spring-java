package com.ifsc.tarefas.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsc.tarefas.model.Prioridade;
import com.ifsc.tarefas.model.Status;
import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.TarefaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
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
        return "lista";//vai dizer qual template vou usar
    }

    @GetMapping("/nova-tarefa") //página para criar uma nova tarefa
    String novaTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("listaStatus", Status.values());
        return "nova-tarefa";
    }
    
    @PostMapping("/salvar")//api que vai receber os dados do formulário
    String salvar(@ModelAttribute("tarefa") Tarefa tarefa) { // @ModelAttribute indica que o objeto tarefa será preenchido com os dados do formulário
        tarefaRepository.save(tarefa);
        return "redirect:/templates/listar"; //redireciona para a página de listagem após salvar
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicao(@PathVariable Long id, @ModelAttribute Tarefa tarefa, RedirectAttributes redirectAttributes) {
        tarefa.setId(id);
        tarefaRepository.save(tarefa);
        redirectAttributes.addFlashAttribute("mensagem", "Tarefa atualizada com sucesso!");
        return "redirect:/tarefas/listar";
    }

    @PostMapping("/deletar/{id}")
    public String deletarTarefa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tarefaRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Tarefa deletada com sucesso!");
        return "redirect:/tarefas/listar";
    }
}
