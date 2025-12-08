package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.AlternativaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.UsuarioIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRespostaRequestDTO {

    private Date dataResposta;

    @NotNull(message = "O usuário não pode ser nulo")
    @Valid
    private UsuarioIdDTO usuario; // ⬅️ Usar SimplifiedDTO

    @NotNull(message = "A questão não pode ser nula")
    @Valid
    private QuestaoIdDTO questao; // ⬅️ Usar SimplifiedDTO

    @NotNull(message = "A alternativa escolhida não pode ser nula")
    @Valid
    private AlternativaIdDTO alternativaEscolhida; // ⬅️ Usar SimplifiedDTO
}
