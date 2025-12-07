package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

//lembrar de colocar entity
@Data
@Table(name = "usuario")
@Entity
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O email não pode ser nulo")
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @NotNull(message = "A senha não pode ser nulo")
    @NotBlank(message = "A senha é obrigatório")
    @Column(name = "senha", length = 255, nullable = false)
    private String senha;

    @NotNull(message = "O cpf não pode ser nulo")
    @NotBlank(message = "O cpf é obrigatório")
    @CPF(message = "O CPF é inválido")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "A dataNascimento não pode ser nula")
    @NotBlank(message = "A dataNascimento é obrigatória")
    @Column(name = "dataNascimento", length = 20, nullable = false)
    private Date dataNascimento;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "celular", length = 12, nullable = false)
    @NotNull(message = "O celular não pode ser nulo")
    @NotBlank(message = "O celular é obrigatório")
    private String celular;

    @Column(name = "telefone", length = 11, nullable = false)
    @NotNull(message = "O telefone não pode ser nulo")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @Column(name = "telefone", nullable = false)
    @NotNull(message = "O sexo não pode ser nulo")
    @NotBlank(message = "O sexo é obrigatório")
    private Boolean sexo;

    @Column(name = "perfil", length = 32, nullable = false)
    @NotNull(message = "O perfil não pode ser nulo")
    @NotBlank(message = "O perfil é obrigatório")
    private String perfil;
}
