package br.com.dbug.questlab.rest.dto.response;

import br.com.dbug.questlab.rest.dto.simplified.DisciplinaIdDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssuntoResponseDTO {
    private Integer id;
    private String nome;
    private DisciplinaIdDTO disciplina;
}