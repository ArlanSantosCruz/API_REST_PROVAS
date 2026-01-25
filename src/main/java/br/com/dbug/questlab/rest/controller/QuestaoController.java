package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.request.QuestaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.QuestaoResponseDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioDisciplinaDTO;
import br.com.dbug.questlab.service.QuestaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gestão de Questões
 *
 * Endpoints disponíveis:
 * - CRUD de questões
 * - Anular/Reativar questões
 * - Relatórios
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/questoes")
@RequiredArgsConstructor
@Tag(name = "Questões", description = "APIs para gerenciamento de questões")
public class QuestaoController {

    private final QuestaoService questaoService;

    /**
     * Cria uma nova questão
     */
    @PostMapping
    @Operation(summary = "Criar questão", description = "Cria uma nova questão no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Questão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Prova ou Assunto não encontrado")
    })
    public ResponseEntity<QuestaoResponseDTO> criar(
            @Valid @RequestBody QuestaoRequestDTO request) {

        log.info("Requisição para criar questão: {}", request);

        QuestaoResponseDTO response = questaoService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Busca questão por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar questão por ID", description = "Retorna uma questão específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questão encontrada"),
            @ApiResponse(responseCode = "404", description = "Questão não encontrada")
    })
    public ResponseEntity<QuestaoResponseDTO> buscarPorId(
            @Parameter(description = "ID da questão") @PathVariable Integer id) {

        log.info("Requisição para buscar questão ID: {}", id);

        QuestaoResponseDTO response = questaoService.findById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas as questões com paginação
     */
    @GetMapping
    @Operation(summary = "Listar questões", description = "Lista todas as questões com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<Page<QuestaoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        log.info("Requisição para listar questões com paginação: {}", pageable);

        Page<QuestaoResponseDTO> response = questaoService.findAll(pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza uma questão existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar questão", description = "Atualiza os dados de uma questão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questão atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou questão anulada"),
            @ApiResponse(responseCode = "404", description = "Questão não encontrada")
    })
    public ResponseEntity<QuestaoResponseDTO> atualizar(
            @Parameter(description = "ID da questão") @PathVariable Integer id,
            @Valid @RequestBody QuestaoRequestDTO request) {

        log.info("Requisição para atualizar questão ID {}: {}", id, request);

        QuestaoResponseDTO response = questaoService.update(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Remove uma questão
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar questão", description = "Remove uma questão do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Questão removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Questão não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da questão") @PathVariable Integer id) {

        log.info("Requisição para deletar questão ID: {}", id);

        questaoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Anula uma questão
     */
    @PatchMapping("/{id}/anular")
    @Operation(summary = "Anular questão", description = "Marca uma questão como anulada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Questão anulada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Questão já está anulada"),
            @ApiResponse(responseCode = "404", description = "Questão não encontrada")
    })
    public ResponseEntity<Void> anular(
            @Parameter(description = "ID da questão") @PathVariable Integer id,
            @Parameter(description = "Motivo da anulação") @RequestParam String motivo) {

        log.info("Requisição para anular questão ID: {} - Motivo: {}", id, motivo);

        questaoService.anular(id, motivo);

        return ResponseEntity.noContent().build();
    }

    /**
     * Reativa uma questão anulada
     */
    @PatchMapping("/{id}/reativar")
    @Operation(summary = "Reativar questão", description = "Reativa uma questão previamente anulada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Questão reativada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Questão não está anulada"),
            @ApiResponse(responseCode = "404", description = "Questão não encontrada")
    })
    public ResponseEntity<Void> reativar(
            @Parameter(description = "ID da questão") @PathVariable Integer id) {

        log.info("Requisição para reativar questão ID: {}", id);

        questaoService.reativar(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * UC-01: Gera relatório de questões por disciplina
     *
     * Retorna um relatório com:
     * - Nome da disciplina
     * - Total de questões ativas (não anuladas)
     * - Total de questões anuladas
     * - Total geral
     *
     * @return Lista de relatórios agrupados por disciplina
     */
    @GetMapping("/relatorios/por-disciplina")
    @Operation(
            summary = "UC-01: Relatório de Questões por Disciplina",
            description = "Gera relatório com quantidade de questões (ativas e anuladas) agrupadas por disciplina"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    })
    public ResponseEntity<List<RelatorioDisciplinaDTO>> gerarRelatorioPorDisciplina() {

        log.info("Requisição para gerar relatório de questões por disciplina");

        List<RelatorioDisciplinaDTO> relatorio = questaoService.gerarRelatorioPorDisciplina();

        log.info("Relatório gerado com {} disciplinas", relatorio.size());

        return ResponseEntity.ok(relatorio);
    }
}