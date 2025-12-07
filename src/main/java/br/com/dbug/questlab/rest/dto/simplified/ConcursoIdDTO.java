package br.com.dbug.questlab.rest.dto.simplified;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConcursoIdDTO {
    private Integer id;
    private String nome;
    private Integer ano;
}