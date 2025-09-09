package com.ifsc.tarefas.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ifsc.tarefas.model.Categoria;
import com.ifsc.tarefas.model.Prioridade;
import com.ifsc.tarefas.model.Status;
import com.ifsc.tarefas.model.Tarefa;
import com.ifsc.tarefas.repository.CategoriaRepository;
import com.ifsc.tarefas.repository.TarefaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/templates")
public class TemplateService {

    private final TarefaRepository tarefaRepository;
    private final CategoriaRepository categoriaRepository;

    public TemplateService(TarefaRepository tarefaRepository, CategoriaRepository categoriaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/listar") // página para listar as tarefas
    // model serve para adicionar atributos que serão usados no template (o html)
    String listarTarefas(Model model, @RequestParam(required = false) String titulo, @RequestParam(required = false) String responsavel, 
    @RequestParam(required = false) Status  status, @RequestParam(required = false) Prioridade prioridade) {
        var tarefas = tarefaRepository.findAll();
        if (titulo != null && !titulo.trim().isEmpty()) {
            tarefas = tarefas.stream().filter(t -> t.getTitulo().toLowerCase().contains(titulo.toLowerCase())).toList();
        }
        if (responsavel != null && !responsavel.trim().isEmpty()) {
            tarefas = tarefas.stream().filter(t -> t.getResponsavel().toLowerCase().contains(responsavel.toLowerCase())).toList();
        }
        if (status != null) {
            tarefas = tarefas.stream().filter(t -> t.getStatus() == status).toList();
            
        }
        if (prioridade != null) {
            tarefas = tarefas.stream().filter(t -> t.getPrioridade() == prioridade).toList();
        }

        model.addAttribute("tarefas", tarefas);
        return "listar";// vai dizer qual template vou usar
    }

    @GetMapping("/tarefa") // página para criar uma nova tarefa
    String novaTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("listaStatus", Status.values());
        return "tarefa";
    }

    @PostMapping("/salvar") // api que vai receber os dados do formulário
    String salvar(@ModelAttribute("tarefa") Tarefa tarefa) { // @ModelAttribute indica que o objeto tarefa será
                                                             // preenchido com os dados do formulário
        tarefaRepository.save(tarefa);
        return "redirect:/templates/listar"; // redireciona para a página de listagem após salvar
    }

    @PostMapping("{id}/excluir")
    String excluir(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
        return "redirect:/templates/listar";
    }

    @GetMapping("/{id}/editar")
    String editar(@PathVariable Long id, Model model) {
        // vai procurar tarefas pelo id, se n achar
        var tarefa = tarefaRepository.findById(id).orElse(null);
        if (tarefa == null) {
            // retornar para pagina inicial
            return "redirect:/templates/listar";
        }
        // adiciona a tarefa que vai ser editada e todo o resto ao template do
        // formulario
        model.addAttribute("tarefa", tarefa);
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("listaStatus", Status.values());
        return "tarefa";
    }

    @GetMapping("/{tarefaId}/associar-categoria")
    String associarTarefaParaUmaCategoria(Model model, @PathVariable Long tarefaId) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getNome());
        }
        var tarefa = tarefaRepository.findById(tarefaId);
        model.addAttribute("tarefa", tarefa.get());

        return "gerenciar-categoria";
    }

    @PostMapping("/{tarefaId}/associar-categoria/{categoriaId}")
    String associarTarefaParaUmaCategoria(@PathVariable Long tarefaId, @RequestParam Long categoriaId) {
        //pega a tarefa e a categoria pelo id
        var tarefa = tarefaRepository.findById(tarefaId);
        var categoria = categoriaRepository.findById(categoriaId);

        if (tarefa.isEmpty() || categoria.isEmpty()) {
            return "redirect:/templates/listar";
        }

        tarefa.get().getCategorias().add(categoria.get());
        tarefaRepository.save(tarefa.get());   
        return "redirect:/templates/listar";
    }

}
