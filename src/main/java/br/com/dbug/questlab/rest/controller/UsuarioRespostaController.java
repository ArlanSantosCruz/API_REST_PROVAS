package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.service.UsuarioRespostaService;
import br.com.dbug.questlab.rest.dto.filter.RelatorioDesempenhoFilterDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioDesempenhoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/usuario-resposta")
@RequiredArgsConstructor
@Tag(name = "Usuario Resposta")
public class UsuarioRespostaController {

    private final UsuarioRespostaService service;  // ← DEVE TER ESTA LINHA

    // ... outros métodos

    @GetMapping("/relatorio/desempenho")
    @Operation(summary = "Relatório de desempenho por usuário")
    public ResponseEntity<RelatorioDesempenhoDTO> relatorioDesempenhoPorUsuario(
            @RequestParam Integer usuarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        RelatorioDesempenhoFilterDTO filter = new RelatorioDesempenhoFilterDTO(
                usuarioId, dataInicial, dataFinal
        );

        RelatorioDesempenhoDTO relatorio = service.relatorioDesempenhoPorUsuario(filter);
        return ResponseEntity.ok(relatorio);
    }
}