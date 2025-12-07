package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class ConcursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "ano", nullable = false)
    private Date ano;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    //RELACIONAMENTO COM BANCA (FK)
    @ManyToOne
    @JoinColumn(name = "idBanca")
    private int idBanca;

     //@Column(name = "id_banca", nullable = false)
     //private int idBanca;

    //RELACIONAMENTO COM INSTITUICAO (FK)

    @ManyToOne
    @JoinColumn(name = "id_Instituicao")
    private int id_Instituicao;

    //@Column(name = "id_Instituicao", nullable = false)
    //private int id_Instituicao;
}

