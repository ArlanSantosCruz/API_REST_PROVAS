package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.QuestaoFilterDTO;
import br.com.dbug.questlab.rest.dto.request.QuestaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.QuestaoResponseDTO;
import br.com.dbug.questlab.service.QuestaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/questoes")
@RequiredArgsConstructor
@Tag(name = "Questões", description = "Gerenciamento de questões")
public class QuestaoController {

    private final QuestaoService questaoService;

    @PostMapping
    @Operation(summary = "Criar nova questão")
    public ResponseEntity<QuestaoResponseDTO> create(@Valid @RequestBody QuestaoRequestDTO request) {
        log.info("Criando nova questão");
        QuestaoResponseDTO response = questaoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar questão por ID")
    public ResponseEntity<QuestaoResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando questão ID: {}", id);
        QuestaoResponseDTO response = questaoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as questões")
    public ResponseEntity<List<QuestaoResponseDTO>> findAll() {
        log.info("Listando todas as questões");
        List<QuestaoResponseDTO> response = questaoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar questões com paginação e filtros")
    public ResponseEntity<Page<QuestaoResponseDTO>> findAllPaginated(
            @ModelAttribute QuestaoFilterDTO filter) {
        log.info("Listando questões com filtros");
        Page<QuestaoResponseDTO> response = questaoService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar questão")
    public ResponseEntity<QuestaoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody QuestaoRequestDTO request) {
        log.info("Atualizando questão ID: {}", id);
        QuestaoResponseDTO response = questaoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir questão")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo questão ID: {}", id);
        questaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/anular")
    @Operation(summary = "Anular questão")
    public ResponseEntity<Void> anular(
            @PathVariable Integer id,
            @RequestParam String motivo) {
        log.info("Anulando questão ID: {} - Motivo: {}", id, motivo);
        questaoService.anular(id, motivo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    @Operation(summary = "Reativar questão")
    public ResponseEntity<Void> reativar(@PathVariable Integer id) {
        log.info("Reativando questão ID: {}", id);
        questaoService.reativar(id);
        return ResponseEntity.noContent().build();
    }
}