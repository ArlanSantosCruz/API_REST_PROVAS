package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaFilterDTO {
    private String texto;
    private String enunciado;
    private Boolean correta;
    private Integer questaoId;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private String sortDirection = "ASC";
}
