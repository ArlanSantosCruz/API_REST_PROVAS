package br.com.dbug.questlab.rest.dto.simplified;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIdDTO {
    private Integer id;
    private String nome;
    private String email;
    private String perfil;
}