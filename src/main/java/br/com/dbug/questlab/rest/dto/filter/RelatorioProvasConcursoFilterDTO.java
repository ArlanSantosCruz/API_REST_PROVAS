package br.com.dbug.questlab.rest.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioProvasConcursoFilterDTO {
    private Integer concursoId;
    private Integer ano;
    private Integer page = 0;
    private Integer size = 10;
}
