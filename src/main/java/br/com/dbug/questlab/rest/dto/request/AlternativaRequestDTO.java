package br.com.dbug.questlab.rest.dto.request;


import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaRequestDTO {
    @NotBlank(message = "Texto é obrigatório")
    @Size(min = 1, max = 500, message = "Texto deve ter entre 1 e 500 caracteres")
    private String texto;

    @NotBlank(message = "Enunciado é obrigatório")
    @Size(min = 1, max = 500, message = "Enunciado deve ter entre 1 e 500 caracteres")
    private String enunciado;

    @NotNull(message = "Indicador de correção é obrigatório")
    private Boolean correta;

    @NotNull(message = "Questão é obrigatória")
    private QuestaoIdDTO questao;
}
