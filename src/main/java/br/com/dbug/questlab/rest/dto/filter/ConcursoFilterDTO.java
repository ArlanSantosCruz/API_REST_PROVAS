package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConcursoFilterDTO {
    private String nome;
    private Integer ano;
    private Boolean cancelado;
    private Boolean ativo;
    private Integer bancaId;
    private Integer instituicaoId;
    private Date dataInicio;
    private Date dataFim;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "nome";
    private String sortDirection = "ASC";
}
