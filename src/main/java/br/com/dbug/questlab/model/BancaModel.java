package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "banca")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BancaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "razaoSocial", length = 150, nullable = false)
    @NotNull(message = "A razão social não pode ser nula")
    @NotBlank(message = "A razão social é obrigatória")
    private String nome;

    @Column(name = "sigla", length = 5, nullable = false, unique = true)
    @NotNull(message = "A sigla não pode ser nula")
    @NotBlank(message = "A sigla é obrigatória")
    private String sigla;

    @Column(name = "cnpj", length = 14, nullable = false, unique = true)
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
    private Boolean ativo = true;
}