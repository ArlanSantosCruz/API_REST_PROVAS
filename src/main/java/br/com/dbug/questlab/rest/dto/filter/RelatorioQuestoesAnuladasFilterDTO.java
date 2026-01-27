package br.com.dbug.questlab.rest.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioQuestoesAnuladasFilterDTO {
    @NotNull(message = "Data inicial é obrigatória")
    private LocalDate dataInicial;

    @NotNull(message = "Data final é obrigatória")
    private LocalDate dataFinal;

    private Integer page = 0;
    private Integer size = 10;
}
