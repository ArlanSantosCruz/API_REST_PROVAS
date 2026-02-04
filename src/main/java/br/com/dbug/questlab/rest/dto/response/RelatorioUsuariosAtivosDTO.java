package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioUsuariosAtivosDTO {
    private String nome;
    private String email;
    private String perfil;
    private LocalDate dataNascimento;

}