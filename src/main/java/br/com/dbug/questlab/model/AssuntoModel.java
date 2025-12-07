package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssuntoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    //RELACIONAMENTO COM Disciplina (FK)
    @ManyToOne
    @JoinColumn(name = "idDisciplina")
    private int idDisciplina;

    //@Column(name = "idDisciplina", nullable = false)
    //private int idDisciplina;
}
