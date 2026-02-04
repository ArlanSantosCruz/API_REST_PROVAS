package br.com.dbug.questlab.rest.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioParticipacaoFilterDTO {
    private Integer mes;  // 1-12
    private Integer ano;  // 2024, 2025, etc
}