package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancaFilterDTO {
    private String razaoSocial;
    private String sigla;
    private String cnpj;
    private Boolean ativo;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "razaoSocial";
    private String sortDirection = "ASC";
}