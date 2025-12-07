package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.ProvaFilterDTO;
import br.com.dbug.questlab.rest.dto.request.ProvaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.ProvaResponseDTO;
import br.com.dbug.questlab.service.ProvaService;
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
@RequestMapping("/api/provas")
@RequiredArgsConstructor
@Tag(name = "Provas", description = "Gerenciamento de provas")
public class ProvaController {

    private final ProvaService provaService;

    @PostMapping
    @Operation(summary = "Criar nova prova")
    public ResponseEntity<ProvaResponseDTO> create(@Valid @RequestBody ProvaRequestDTO request) {
        log.info("Criando nova prova: {}", request.getNome());
        ProvaResponseDTO response = provaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar prova por ID")
    public ResponseEntity<ProvaResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando prova ID: {}", id);
        ProvaResponseDTO response = provaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as provas")
    public ResponseEntity<List<ProvaResponseDTO>> findAll() {
        log.info("Listando todas as provas");
        List<ProvaResponseDTO> response = provaService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar provas com paginação e filtros")
    public ResponseEntity<Page<ProvaResponseDTO>> findAllPaginated(
            @ModelAttribute ProvaFilterDTO filter) {
        log.info("Listando provas com filtros");
        Page<ProvaResponseDTO> response = provaService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar prova")
    public ResponseEntity<ProvaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProvaRequestDTO request) {
        log.info("Atualizando prova ID: {}", id);
        ProvaResponseDTO response = provaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir prova")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo prova ID: {}", id);
        provaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}