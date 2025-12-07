package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplinaFilterDTO {
    private String nome;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "nome";
    private String sortDirection = "ASC";
}