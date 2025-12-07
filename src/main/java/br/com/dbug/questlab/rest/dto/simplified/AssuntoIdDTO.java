package br.com.dbug.questlab.rest.dto.simplified;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssuntoIdDTO {
    private Integer id;
    private String nome;
    private DisciplinaIdDTO disciplina;
}