package br.com.dbug.questlab.rest.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDesempenhoFilterDTO {
    private Integer usuarioId;
    private LocalDate dataInicial;  // opcional
    private LocalDate dataFinal;    // opcional
}