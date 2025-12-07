package br.com.dbug.questlab.rest.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestaoFilterDTO {
    private String enunciado;
    private String comentarioProfessor;
    private Boolean anulada;
    private Integer provaId;
    private Integer assuntoId;
    private Integer disciplinaId;
    private Integer concursoId;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private String sortDirection = "ASC";
}