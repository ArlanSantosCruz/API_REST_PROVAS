package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.AlternativaFilterDTO;
import br.com.dbug.questlab.rest.dto.request.AlternativaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AlternativaResponseDTO;
import br.com.dbug.questlab.service.AlternativaService;
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
@RequestMapping("/api/alternativas")
@RequiredArgsConstructor
@Tag(name = "Alternativas", description = "Gerenciamento de alternativas")
public class AlternativaController {

    private final AlternativaService alternativaService;

    @PostMapping
    @Operation(summary = "Criar nova alternativa")
    public ResponseEntity<AlternativaResponseDTO> create(@Valid @RequestBody AlternativaRequestDTO request) {
        log.info("Criando nova alternativa");
        AlternativaResponseDTO response = alternativaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alternativa por ID")
    public ResponseEntity<AlternativaResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando alternativa ID: {}", id);
        AlternativaResponseDTO response = alternativaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as alternativas")
    public ResponseEntity<List<AlternativaResponseDTO>> findAll() {
        log.info("Listando todas as alternativas");
        List<AlternativaResponseDTO> response = alternativaService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar alternativas com paginação e filtros")
    public ResponseEntity<Page<AlternativaResponseDTO>> findAllPaginated(
            @ModelAttribute AlternativaFilterDTO filter) {
        log.info("Listando alternativas com filtros");
        Page<AlternativaResponseDTO> response = alternativaService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alternativa")
    public ResponseEntity<AlternativaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AlternativaRequestDTO request) {
        log.info("Atualizando alternativa ID: {}", id);
        AlternativaResponseDTO response = alternativaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alternativa")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo alternativa ID: {}", id);
        alternativaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}