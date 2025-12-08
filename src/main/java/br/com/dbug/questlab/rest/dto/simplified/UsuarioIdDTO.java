package br.com.dbug.questlab.rest.dto.simplified;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioIdDTO {
    private Integer id;
    private String nome;
    private String email;
    private String perfil;
}