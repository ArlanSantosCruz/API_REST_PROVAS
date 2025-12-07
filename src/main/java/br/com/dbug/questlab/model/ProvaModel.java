package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class ProvaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "dataAplicacao", nullable = false)
    private Date dataAplicacao;

    //RELACIONAMENTO COM Consurso (FK)
    @ManyToOne
    @JoinColumn(name = "idConcurso")
    private int idConcurso;

    //@Column(name = "idConcurso", nullable = false)
    //private int idConcurso;

    //RELACIONAMENTO COM Cargo (FK)
    @ManyToOne
    @JoinColumn(name = "idCargo")
    private int idCargo;

    //@Column(name = "idCargo", nullable = false)
    //private int idCargo;
}
