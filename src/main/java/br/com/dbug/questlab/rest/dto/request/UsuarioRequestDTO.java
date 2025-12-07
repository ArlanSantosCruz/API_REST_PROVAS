package br.com.dbug.questlab.rest.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatória")
    private Date dataNascimento;

    @NotBlank(message = "Sexo é obrigatório")
    @Size(min = 1, max = 1, message = "Sexo deve ser M, F ou O")
    private String sexo;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;

    @NotBlank(message = "Perfil é obrigatório")
    private String perfil; // ADMIN, PROFESSOR, ALUNO

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @NotBlank(message = "Celular é obrigatório")
    @Size(min = 10, max = 11, message = "Celular deve ter entre 10 e 11 caracteres")
    private String celular;

    @Size(max = 11, message = "Telefone deve ter no máximo 11 caracteres")
    private String telefone;

    private Boolean ativo = true;
}
