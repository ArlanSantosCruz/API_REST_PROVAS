package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.AssuntoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ProvaIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoRequestDTO {

    @NotNull(message = "O enunciado não pode ser nulo")
    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    private String comentarioProfessor;

    @NotNull(message = "A prova não pode ser nula")
    @Valid
    private ProvaIdDTO prova; // ⬅️ Usar SimplifiedDTO

    @NotNull(message = "O assunto não pode ser nulo")
    @Valid
    private AssuntoIdDTO assunto; // ⬅️ Usar SimplifiedDTO
}