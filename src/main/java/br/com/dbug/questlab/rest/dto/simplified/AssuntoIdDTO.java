package br.com.dbug.questlab.rest.dto.simplified;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssuntoIdDTO {
    private Integer id;
    private String nome;
    private DisciplinaIdDTO disciplina;
}