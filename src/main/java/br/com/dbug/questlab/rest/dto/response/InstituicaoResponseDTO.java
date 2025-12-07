package br.com.dbug.questlab.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituicaoResponseDTO {
    private Integer id;
    private String razaoSocial;
    private String sigla;
    private String cnpj;
    private String email;
    private String telefone;
    private Boolean ativo;
}