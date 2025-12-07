package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioRespostaFilterDTO {
    private Integer usuarioId;
    private Integer questaoId;
    private Integer alternativaId;
    private Integer disciplinaId;
    private Integer assuntoId;
    private Date dataRespostaInicio;
    private Date dataRespostaFim;
    private Boolean acertou; // Filtro por acerto/erro
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "dataResposta";
    private String sortDirection = "DESC";
}