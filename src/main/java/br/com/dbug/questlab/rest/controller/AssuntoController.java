package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.AssuntoFilterDTO;
import br.com.dbug.questlab.rest.dto.request.AssuntoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AssuntoResponseDTO;
import br.com.dbug.questlab.service.AssuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/assuntos")
@RequiredArgsConstructor
@Tag(name = "Assuntos", description = "Gerenciamento de assuntos por disciplina")
public class AssuntoController {

    private final AssuntoService assuntoService;

    @PostMapping
    @Operation(summary = "Criar novo assunto")
    public ResponseEntity<AssuntoResponseDTO> create(@Valid @RequestBody AssuntoRequestDTO request) {
        log.info("Criando novo assunto: {}", request.getNome());
        AssuntoResponseDTO response = assuntoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar assunto por ID")
    public ResponseEntity<AssuntoResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando assunto ID: {}", id);
        AssuntoResponseDTO response = assuntoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os assuntos")
    public ResponseEntity<List<AssuntoResponseDTO>> findAll() {
        log.info("Listando todos os assuntos");
        List<AssuntoResponseDTO> response = assuntoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    @Operation(summary = "Listar assuntos por disciplina")
    public ResponseEntity<List<AssuntoResponseDTO>> findByDisciplinaId(
            @PathVariable Integer disciplinaId) {
        log.info("Listando assuntos da disciplina ID: {}", disciplinaId);
        List<AssuntoResponseDTO> response = assuntoService.findByDisciplinaId(disciplinaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar assuntos com paginação e filtros")
    public ResponseEntity<Page<AssuntoResponseDTO>> findAllPaginated(
            @ModelAttribute AssuntoFilterDTO filter) {
        log.info("Listando assuntos com filtros");
        Page<AssuntoResponseDTO> response = assuntoService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar assunto")
    public ResponseEntity<AssuntoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AssuntoRequestDTO request) {
        log.info("Atualizando assunto ID: {}", id);
        AssuntoResponseDTO response = assuntoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir assunto")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo assunto ID: {}", id);
        assuntoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}