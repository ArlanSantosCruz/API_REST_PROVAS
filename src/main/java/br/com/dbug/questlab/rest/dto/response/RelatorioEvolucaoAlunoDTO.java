package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioEvolucaoAlunoDTO {
    private String disciplina;
    private String assunto;
    private Long totalTentativas;
    private Double percentualAcertos;  // Calculado no Service

    // Construtor auxiliar (sem percentual - será calculado)
    public RelatorioEvolucaoAlunoDTO(String disciplina, String assunto, Long totalTentativas, Long totalAcertos) {
        this.disciplina = disciplina;
        this.assunto = assunto;
        this.totalTentativas = totalTentativas;
        this.percentualAcertos = calcularPercentual(totalTentativas, totalAcertos);
    }

    private Double calcularPercentual(Long total, Long acertos) {
        if (total == null || total == 0) {
            return 0.0;
        }
        return (acertos.doubleValue() / total.doubleValue()) * 100.0;
    }
}