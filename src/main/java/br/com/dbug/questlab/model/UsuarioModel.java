package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull(message = "A senha não pode ser nula")
    @NotBlank(message = "A senha é obrigatória")
    @Column(name = "senha", length = 255, nullable = false)
    private String senha;

    @NotNull(message = "O cpf não pode ser nulo")
    @NotBlank(message = "O cpf é obrigatório")
    @CPF(message = "O CPF é inválido")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "A dataNascimento não pode ser nula")
    @Column(name = "dataNascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "celular", length = 11, nullable = false)
    @NotNull(message = "O celular não pode ser nulo")
    @NotBlank(message = "O celular é obrigatório")
    private String celular;

    @Column(name = "telefone", length = 11)
    private String telefone;

    @Column(name = "sexo", length = 1, nullable = false)
    @NotNull(message = "O sexo não pode ser nulo")
    @NotBlank(message = "O sexo é obrigatório")
    private String sexo;

    @Column(name = "perfil", length = 32, nullable = false)
    @NotNull(message = "O perfil não pode ser nulo")
    @NotBlank(message = "O perfil é obrigatório")
    private String perfil;
}
