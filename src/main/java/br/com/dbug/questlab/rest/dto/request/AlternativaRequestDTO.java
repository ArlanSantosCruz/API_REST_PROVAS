package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaRequestDTO {

    @NotNull(message = "O texto não pode ser nulo")
    @NotBlank(message = "O texto é obrigatório")
    private String texto;

    @NotNull(message = "O enunciado não pode ser nulo")
    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    @NotNull(message = "O campo correta não pode ser nulo")
    private Boolean correta;

    @NotNull(message = "A questão não pode ser nula")
    @Valid
    private QuestaoIdDTO questao; // ⬅️ Usar SimplifiedDTO
}