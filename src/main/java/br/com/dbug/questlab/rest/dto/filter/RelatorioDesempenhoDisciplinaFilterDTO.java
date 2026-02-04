package br.com.dbug.questlab.rest.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDesempenhoDisciplinaFilterDTO {
    private Integer disciplinaId;
    private Integer concursoId;      // opcional
    private LocalDate dataInicial;
    private LocalDate dataFinal;
}