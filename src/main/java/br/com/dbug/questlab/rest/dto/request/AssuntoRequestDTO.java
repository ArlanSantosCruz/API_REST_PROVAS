package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.DisciplinaIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssuntoRequestDTO {

    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A disciplina não pode ser nula")
    @Valid
    private DisciplinaIdDTO disciplina; // ⬅️ Usar SimplifiedDTO

    public Integer getDisciplinaId() {
        return 0;
    }
}
