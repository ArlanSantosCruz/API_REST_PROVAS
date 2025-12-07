package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioFilterDTO {
    private String nome;
    private String email;
    private String perfil;
    private String cpf;
    private Boolean ativo;
    private Date dataNascimentoInicio;
    private Date dataNascimentoFim;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "nome";
    private String sortDirection = "ASC";
}