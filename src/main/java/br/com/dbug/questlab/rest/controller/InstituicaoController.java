package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.InstituicaoFilterDTO;
import br.com.dbug.questlab.rest.dto.request.InstituicaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.InstituicaoResponseDTO;
import br.com.dbug.questlab.service.InstituicaoService; // ⬅️ SERVICE, não Repository!
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
@RequestMapping("/api/instituicoes")
@RequiredArgsConstructor
@Tag(name = "Instituições", description = "Gerenciamento de instituições")
public class InstituicaoController {

    // ⬅️ IMPORTANTE: Injetar o SERVICE, não o Repository!
    private final InstituicaoService instituicaoService;

    @PostMapping
    @Operation(summary = "Criar nova instituição")
    public ResponseEntity<InstituicaoResponseDTO> create(
            @Valid @RequestBody InstituicaoRequestDTO request) {
        log.info("Criando nova instituição: {}", request.getSigla());
        InstituicaoResponseDTO response = instituicaoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instituição por ID")
    public ResponseEntity<InstituicaoResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando instituição ID: {}", id);
        InstituicaoResponseDTO response = instituicaoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as instituições")
    public ResponseEntity<List<InstituicaoResponseDTO>> findAll() {
        log.info("Listando todas as instituições");
        List<InstituicaoResponseDTO> response = instituicaoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar instituições com paginação e filtros")
    public ResponseEntity<Page<InstituicaoResponseDTO>> findAllPaginated(
            @ModelAttribute InstituicaoFilterDTO filter) {
        log.info("Listando instituições com filtros");
        Page<InstituicaoResponseDTO> response = instituicaoService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar instituição")
    public ResponseEntity<InstituicaoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody InstituicaoRequestDTO request) {
        log.info("Atualizando instituição ID: {}", id);
        InstituicaoResponseDTO response = instituicaoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir instituição")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo instituição ID: {}", id);
        instituicaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar instituição")
    public ResponseEntity<Void> inactivate(@PathVariable Integer id) {
        log.info("Inativando instituição ID: {}", id);
        instituicaoService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar instituição")
    public ResponseEntity<Void> activate(@PathVariable Integer id) {
        log.info("Ativando instituição ID: {}", id);
        instituicaoService.activate(id);
        return ResponseEntity.noContent().build();
    }
}