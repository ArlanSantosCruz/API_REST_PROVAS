package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.RelatorioParticipacaoFilterDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioParticipacaoDTO;
import br.com.dbug.questlab.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.dbug.questlab.rest.dto.response.RelatorioIndicadoresDTO;


@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoints para relatórios gerais da plataforma")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/participacao-plataforma")
    @Operation(summary = "Relatório geral de participação da plataforma")
    public ResponseEntity<RelatorioParticipacaoDTO> relatorioParticipacaoPlataforma(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {

        RelatorioParticipacaoFilterDTO filter = new RelatorioParticipacaoFilterDTO(mes, ano);
        RelatorioParticipacaoDTO relatorio = relatorioService.relatorioParticipacaoPlataforma(filter);

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/indicadores")
    @Operation(summary = "Relatório sintético de indicadores de aprendizado",
            description = "Consolida todos os indicadores estratégicos da plataforma")
    public ResponseEntity<RelatorioIndicadoresDTO> relatorioIndicadores() {
        RelatorioIndicadoresDTO relatorio = relatorioService.relatorioIndicadores();
        return ResponseEntity.ok(relatorio);
    }


// ============= EXEMPLO DE RESPOSTA JSON =============
/*
{
    "usuarios": {
        "totalUsuarios": 1500,
        "totalUsuariosAtivos": 980,
        "percentualAtivos": 65.33
    },
    "questoes": {
        "totalQuestoesCadastradas": 5000,
        "totalQuestoesResolvidas": 3200,
        "crescimentoBancoQuestoes": 12.5
    },
    "aprendizado": {
        "percentualMedioAcerto": 72.8,
        "totalTentativas": 45000,
        "totalAcertos": 32760
    }
}
*/
}