package br.com.dbug.questlab.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancaRequestDTO {
    @NotBlank(message = "Razão Social é obrigatória")
    @Size(min = 3, max = 150, message = "Razão Social deve ter entre 3 e 150 caracteres")
    private String razaoSocial;

    @NotBlank(message = "Sigla é obrigatória")
    @Size(min = 2, max = 5, message = "Sigla deve ter entre 2 e 5 caracteres")
    private String sigla;

    @NotBlank(message = "CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "CNPJ deve ter 14 caracteres")
    private String cnpj;

    @NotBlank(message = "Email é obrigatório")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 caracteres")
    private String telefone;

    private Boolean ativo = true;
}
