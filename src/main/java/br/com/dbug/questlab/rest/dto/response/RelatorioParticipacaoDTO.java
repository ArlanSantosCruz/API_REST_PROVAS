package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioParticipacaoDTO {
    private Long totalUsuarios;
    private Long totalUsuariosAtivos;
    private Long totalQuestoesRespondidas;
    private Double crescimentoPercentual;  // Calculado no Service

    // Construtor sem crescimento (será calculado depois)
    public RelatorioParticipacaoDTO(Long totalUsuarios, Long totalUsuariosAtivos, Long totalQuestoesRespondidas) {
        this.totalUsuarios = totalUsuarios;
        this.totalUsuariosAtivos = totalUsuariosAtivos;
        this.totalQuestoesRespondidas = totalQuestoesRespondidas;
        this.crescimentoPercentual = 0.0;  // Será calculado no Service
    }
}