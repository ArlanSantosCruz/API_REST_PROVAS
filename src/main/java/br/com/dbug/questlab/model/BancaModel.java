package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public class BancaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "razaoSocial,", length = 150, nullable = false)
    @NotNull(message = "A razaoSocial, não pode ser nula")
    @NotBlank(message = "A razaoSocial, é obrigatória")
    private String razaoSocial;

    @Column(name = "sigla", length = 5, nullable = false)
    @NotNull(message = "A sigla não pode ser nula")
    @NotBlank(message = "A sigla é obrigatória")
    private String sigla;

    @Column(name = "cnpj", length = 14, nullable = false)
    @NotNull(message = "O cnpj não pode ser nulo")
    @NotBlank(message = "O cnpj é obrigatório")
    private String cnpj;

    @NotNull(message = "O email não pode ser nulo")
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", length = 11, nullable = false)
    @NotNull(message = "O telefone não pode ser nulo")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

}
