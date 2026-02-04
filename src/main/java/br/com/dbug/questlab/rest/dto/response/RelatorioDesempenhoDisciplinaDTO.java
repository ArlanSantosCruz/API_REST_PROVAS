package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDesempenhoDisciplinaDTO {
    private String disciplina;
    private Double mediaAcertos;      // Calculado no Service (%)
    private Long totalTentativas;
    private Long numeroQuestoesAnuladas;

    // Construtor auxiliar
    public RelatorioDesempenhoDisciplinaDTO(
            String disciplina,
            Long totalTentativas,
            Long totalAcertos,
            Long numeroQuestoesAnuladas) {
        this.disciplina = disciplina;
        this.totalTentativas = totalTentativas;
        this.numeroQuestoesAnuladas = numeroQuestoesAnuladas;
        this.mediaAcertos = calcularMedia(totalTentativas, totalAcertos);
    }

    private Double calcularMedia(Long total, Long acertos) {
        if (total == null || total == 0) {
            return 0.0;
        }
        return (acertos.doubleValue() / total.doubleValue()) * 100.0;
    }
}