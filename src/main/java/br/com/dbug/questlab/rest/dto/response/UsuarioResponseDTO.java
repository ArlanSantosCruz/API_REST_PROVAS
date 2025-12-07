package br.com.dbug.questlab.rest.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private Date dataNascimento;
    private String sexo;
    private String perfil;
    private String cpf;
    private String celular;
    private String telefone;
    private Boolean ativo;
}