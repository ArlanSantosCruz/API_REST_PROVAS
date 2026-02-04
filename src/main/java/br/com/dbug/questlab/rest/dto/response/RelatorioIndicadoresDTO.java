package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioIndicadoresDTO {
    private IndicadoresUsuarios usuarios;
    private IndicadoresQuestoes questoes;
    private IndicadoresAprendizado aprendizado;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IndicadoresUsuarios {
        private Long totalUsuarios;
        private Long totalUsuariosAtivos;
        private Double percentualAtivos;  // Calculado
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IndicadoresQuestoes {
        private Long totalQuestoesCadastradas;
        private Long totalQuestoesResolvidas;
        private Double crescimentoBancoQuestoes;  // Calculado
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IndicadoresAprendizado {
        private Double percentualMedioAcerto;  // Calculado
        private Long totalTentativas;
        private Long totalAcertos;
    }
}
