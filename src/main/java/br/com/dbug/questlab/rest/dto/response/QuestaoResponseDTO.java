package br.com.dbug.questlab.rest.dto.response;


import br.com.dbug.questlab.rest.dto.simplified.AssuntoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ProvaIdDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestaoResponseDTO {
    private Integer id;
    private String enunciado;
    private String comentarioProfessor;
    private Boolean anulada;
    private ProvaIdDTO prova;
    private AssuntoIdDTO assunto;
}
