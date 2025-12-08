package br.com.dbug.questlab.rest.dto.simplified;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvaIdDTO {
    private Integer id;
    private String nome;
    private Date dataAplicacao;
}