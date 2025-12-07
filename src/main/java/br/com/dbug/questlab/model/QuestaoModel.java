package br.com.dbug.questlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "enunciado", length = 255, nullable = false)
    @NotNull(message = "O enunciado não pode ser nulo")
    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    @Column(name = "comentarioProfessor", length = 255, nullable = false)
    private String comentarioProfessor;

    @Column(name = "anulada", nullable = false)
    private boolean anulada;

    @Column(name = "idProva", nullable = false)
    private int idProva;

    @Column(name = "idAssunto", nullable = false)
    private int idAssunto;
}
