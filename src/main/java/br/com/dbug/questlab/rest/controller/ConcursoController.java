package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.ConcursoFilterDTO;
import br.com.dbug.questlab.rest.dto.request.ConcursoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.ConcursoResponseDTO;
import br.com.dbug.questlab.service.ConcursoService; // ⬅️ SERVICE, não Repository!
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
@RequestMapping("/api/concursos")
@RequiredArgsConstructor
@Tag(name = "Concursos", description = "Gerenciamento de concursos públicos")
public class ConcursoController {

    // ⬅️ IMPORTANTE: Injetar o SERVICE, não o Repository!
    private final ConcursoService concursoService;

    @PostMapping
    @Operation(summary = "Criar novo concurso")
    public ResponseEntity<ConcursoResponseDTO> create(
            @Valid @RequestBody ConcursoRequestDTO request) {
        log.info("Criando novo concurso: {}", request.getNome());
        ConcursoResponseDTO response = concursoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar concurso por ID")
    public ResponseEntity<ConcursoResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando concurso ID: {}", id);
        ConcursoResponseDTO response = concursoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os concursos")
    public ResponseEntity<List<ConcursoResponseDTO>> findAll() {
        log.info("Listando todos os concursos");
        List<ConcursoResponseDTO> response = concursoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar concursos com paginação e filtros")
    public ResponseEntity<Page<ConcursoResponseDTO>> findAllPaginated(
            @ModelAttribute ConcursoFilterDTO filter) {
        log.info("Listando concursos com filtros");
        Page<ConcursoResponseDTO> response = concursoService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar concurso")
    public ResponseEntity<ConcursoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConcursoRequestDTO request) {
        log.info("Atualizando concurso ID: {}", id);
        ConcursoResponseDTO response = concursoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir concurso")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo concurso ID: {}", id);
        concursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar concurso")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        log.info("Cancelando concurso ID: {}", id);
        concursoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar concurso")
    public ResponseEntity<Void> ativar(@PathVariable Integer id) {
        log.info("Ativando concurso ID: {}", id);
        concursoService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar concurso")
    public ResponseEntity<Void> inativar(@PathVariable Integer id) {
        log.info("Inativando concurso ID: {}", id);
        concursoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}