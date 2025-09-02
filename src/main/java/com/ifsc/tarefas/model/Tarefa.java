package com.ifsc.tarefas.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity //entidade anotação - é uma tabela no banco de dados
public class Tarefa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private = não posso acessar esse atributo fora da classe, outros tipos também usados: public e protected
    // estrutura java: primeiro o tipo da variavel, depois o nome da variavel

    @Column(name = "tarefa_id") // nome da coluna no banco de dados
    private Long id;// nome do atributo no back

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotBlank(message = "O responsável é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do responsável deve ter entre 3 e 100 caracteres")
    private String responsavel;

    private LocalDate dataCriacao = LocalDate.now();

    @FutureOrPresent(message = "A data limite deve ser hoje ou uma data futura")
    private LocalDate dataLimite;

    @ManyToMany // relação de muitos para muitos
    @JoinTable(
        name = "tarefa_categoria",//tabela intermediária
        joinColumns = @JoinColumn(name = "tarefa_id"),//atributo de um objeto que se junta com a tabela intermediária
        inverseJoinColumns = @JoinColumn(name = "categoria_id") //atributo do outro objeto que se junta com a tabela intermediária
    )
    
    private Set<Categoria> categorias = new HashSet<>();



    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório")
    private Status status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Prioridade getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public LocalDate getDataLimite() {
        return dataLimite;
    }
    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }
    public Set<Categoria> getCategorias() {
        return categorias;
    }
    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }
}
