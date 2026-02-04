package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDesempenhoDTO {
    private Long totalQuestoesRespondidas;
    private Long totalAcertos;
    private Long totalErros;
    private Double taxaAproveitamento;  // Percentual calculado no Service

    // Construtor auxiliar para facilitar criação no Service
    public RelatorioDesempenhoDTO(Long totalQuestoesRespondidas, Long totalAcertos, Long totalErros) {
        this.totalQuestoesRespondidas = totalQuestoesRespondidas;
        this.totalAcertos = totalAcertos;
        this.totalErros = totalErros;
        // Taxa de aproveitamento calculada automaticamente
        this.taxaAproveitamento = calcularTaxaAproveitamento(totalQuestoesRespondidas, totalAcertos);
    }

    private Double calcularTaxaAproveitamento(Long total, Long acertos) {
        if (total == null || total == 0) {
            return 0.0;
        }
        return (acertos.doubleValue() / total.doubleValue()) * 100.0;
    }
}
