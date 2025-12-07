package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.DisciplinaFilterDTO;
import br.com.dbug.questlab.rest.dto.request.DisciplinaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.DisciplinaResponseDTO;
import br.com.dbug.questlab.service.DisciplinaService;
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
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
@Tag(name = "Disciplinas", description = "Gerenciamento de disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @PostMapping
    @Operation(summary = "Criar nova disciplina")
    public ResponseEntity<DisciplinaResponseDTO> create(@Valid @RequestBody DisciplinaRequestDTO request) {
        log.info("Criando nova disciplina: {}", request.getNome());
        DisciplinaResponseDTO response = disciplinaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar disciplina por ID")
    public ResponseEntity<DisciplinaResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando disciplina ID: {}", id);
        DisciplinaResponseDTO response = disciplinaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as disciplinas")
    public ResponseEntity<List<DisciplinaResponseDTO>> findAll() {
        log.info("Listando todas as disciplinas");
        List<DisciplinaResponseDTO> response = disciplinaService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar disciplinas com paginação e filtros")
    public ResponseEntity<Page<DisciplinaResponseDTO>> findAllPaginated(
            @ModelAttribute DisciplinaFilterDTO filter) {
        log.info("Listando disciplinas com filtros");
        Page<DisciplinaResponseDTO> response = disciplinaService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar disciplina")
    public ResponseEntity<DisciplinaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody DisciplinaRequestDTO request) {
        log.info("Atualizando disciplina ID: {}", id);
        DisciplinaResponseDTO response = disciplinaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir disciplina")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo disciplina ID: {}", id);
        disciplinaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}