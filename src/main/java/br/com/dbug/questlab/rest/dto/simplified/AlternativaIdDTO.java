package br.com.dbug.questlab.rest.dto.simplified;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaIdDTO {
    private Integer id;
    private String textoResumido;
    private Boolean correta;
}