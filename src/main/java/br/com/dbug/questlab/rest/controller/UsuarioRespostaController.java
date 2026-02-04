package br.com.dbug.questlab.rest.controller;
import br.com.dbug.questlab.rest.dto.response.RelatorioEvolucaoAlunoDTO;
import org.springframework.data.domain.Page;
import br.com.dbug.questlab.service.UsuarioRespostaService;
import br.com.dbug.questlab.rest.dto.filter.RelatorioDesempenhoFilterDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioDesempenhoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.dbug.questlab.rest.dto.filter.RelatorioDesempenhoDisciplinaFilterDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioDesempenhoDisciplinaDTO;
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
    @GetMapping("/relatorio/evolucao")
    @Operation(summary = "Relatório de evolução do aluno por disciplina e assunto")
    public ResponseEntity<Page<RelatorioEvolucaoAlunoDTO>> relatorioEvolucaoAluno(
            @RequestParam Integer usuarioId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<RelatorioEvolucaoAlunoDTO> relatorio = service.relatorioEvolucaoAluno(usuarioId, page, size);
        return ResponseEntity.ok(relatorio);
    }
    @GetMapping("/relatorio/desempenho-disciplina")
    @Operation(summary = "Relatório de desempenho por disciplina")
    public ResponseEntity<RelatorioDesempenhoDisciplinaDTO> relatorioDesempenhoPorDisciplina(
            @RequestParam Integer disciplinaId,
            @RequestParam(required = false) Integer concursoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        RelatorioDesempenhoDisciplinaFilterDTO filter = new RelatorioDesempenhoDisciplinaFilterDTO(
                disciplinaId, concursoId, dataInicial, dataFinal
        );

        RelatorioDesempenhoDisciplinaDTO relatorio = service.relatorioDesempenhoPorDisciplina(filter);
        return ResponseEntity.ok(relatorio);
    }

}