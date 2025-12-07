package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.AlternativaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.UsuarioIdDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioRespostaRequestDTO {
    @NotNull(message = "Data de resposta é obrigatória")
    private Date dataResposta;

    @NotNull(message = "Usuário é obrigatório")
    private UsuarioIdDTO usuario;

    @NotNull(message = "Questão é obrigatória")
    private QuestaoIdDTO questao;

    @NotNull(message = "Alternativa escolhida é obrigatória")
    private AlternativaIdDTO alternativaEscolhida;
}
