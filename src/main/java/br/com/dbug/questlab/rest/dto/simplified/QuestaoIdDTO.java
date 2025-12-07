package br.com.dbug.questlab.rest.dto.simplified;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestaoIdDTO {
    private Integer id;
    private String enunciadoResumido;
    private Boolean anulada;
}
