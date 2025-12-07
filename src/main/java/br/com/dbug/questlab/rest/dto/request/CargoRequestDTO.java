package br.com.dbug.questlab.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
    private String nome;

    @NotBlank(message = "Escolaridade é obrigatória")
    @Size(min = 3, max = 150, message = "Escolaridade deve ter entre 3 e 150 caracteres")
    private String escolaridade;
}