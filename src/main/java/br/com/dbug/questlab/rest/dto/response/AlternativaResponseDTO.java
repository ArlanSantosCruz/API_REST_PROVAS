package br.com.dbug.questlab.rest.dto.response;

import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaResponseDTO {
    private Integer id;
    private String texto;
    private String enunciado;
    private Boolean correta;
    private QuestaoIdDTO questao;
}