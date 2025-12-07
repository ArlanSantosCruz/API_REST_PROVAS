package br.com.dbug.questlab.rest.dto.simplified;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioRespostaIdDTO {
    private Integer id;
    private Date dataResposta;
}