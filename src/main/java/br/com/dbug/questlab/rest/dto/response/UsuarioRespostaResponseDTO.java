package br.com.dbug.questlab.rest.dto.response;


import br.com.dbug.questlab.rest.dto.simplified.AlternativaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.UsuarioIdDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioRespostaResponseDTO {
    private Integer id;
    private Date dataResposta;
    private UsuarioIdDTO usuario;
    private QuestaoIdDTO questao;
    private AlternativaIdDTO alternativaEscolhida;
    // Campo calculado para saber se acertou
    private Boolean acertou;
}