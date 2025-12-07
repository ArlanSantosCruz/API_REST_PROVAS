package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlternativaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private int id;

    @Column(name = "correta", nullable = false)
    private boolean correta;

    @Column(name = "enunciado", length = 255, nullable = false)
    @NotNull(message = "O enunciado não pode ser nulo")
    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    @Column(name = "texto", length = 255, nullable = false)
    @NotNull(message = "O texto não pode ser nulo")
    @NotBlank(message = "O texto é obrigatório")
    private String texto;

}
