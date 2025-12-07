package br.com.dbug.questlab.rest.dto.response;

import br.com.dbug.questlab.rest.dto.simplified.BancaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.InstituicaoIdDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConcursoResponseDTO {
    private Integer id;
    private String nome;
    private Date ano;
    private Boolean cancelado;
    private Boolean ativo;
    private BancaIdDTO banca;
    private InstituicaoIdDTO instituicao;
}