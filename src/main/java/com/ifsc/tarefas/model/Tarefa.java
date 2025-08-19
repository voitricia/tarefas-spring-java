package com.ifsc.tarefas.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;


@Entity //entidade anotação - é uma tabela no banco de dados
public class Tarefa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private = não posso acessar esse atributo fora da classe, outros tipos também usados: public e protected
    // estrutura java: primeiro o tipo da variavel, depois o nome da variavel

    @Column(name = "tarefa_id") // nome da coluna no banco de dados
    private Long id;// nome do atributo no back

    private String titulo;
    private String descricao;
    private String responsavel;
    private LocalDate dataCriacao = LocalDate.now();
    private LocalDate dataLimite;

    @OneToMany(fetch = FetchType.EAGER)//manyToOne - uma tarefa pode ter várias categorias, mas uma categoria só pode estar em uma tarefa | eager - quando eu buscar a tarefa, já traga as categorias dela
    @JoinColumn(name = "categoria_id") // nome da coluna que vai armazenar o id da categoria na tabela tarefa
    private List<Categoria> categoria; // pq a tarefa pode ter várias categorias

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
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




    
}
