package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProvaFilterDTO {
    private String nome;
    private Integer concursoId;
    private Integer cargoId;
    private Date dataAplicacaoInicio;
    private Date dataAplicacaoFim;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "dataAplicacao";
    private String sortDirection = "DESC";
}
