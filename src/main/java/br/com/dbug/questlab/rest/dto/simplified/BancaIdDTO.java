package br.com.dbug.questlab.rest.dto.simplified;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancaIdDTO {
    private Integer id;
    private String razaoSocial;
    private String sigla;
}