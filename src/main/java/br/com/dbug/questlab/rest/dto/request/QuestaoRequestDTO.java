package br.com.dbug.questlab.rest.dto.request;


import br.com.dbug.questlab.rest.dto.simplified.AssuntoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ProvaIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestaoRequestDTO {
    @NotBlank(message = "Enunciado é obrigatório")
    @Size(min = 10, max = 5000, message = "Enunciado deve ter entre 10 e 5000 caracteres")
    private String enunciado;

    @Size(max = 1000, message = "Comentário do professor deve ter no máximo 1000 caracteres")
    private String comentarioProfessor;

    private Boolean anulada = false;

    @NotNull(message = "Prova é obrigatória")
    private ProvaIdDTO prova;

    @NotNull(message = "Assunto é obrigatório")
    private AssuntoIdDTO assunto;
}
