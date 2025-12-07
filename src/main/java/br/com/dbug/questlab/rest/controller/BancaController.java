package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.BancaFilterDTO;
import br.com.dbug.questlab.rest.dto.request.BancaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.BancaResponseDTO;
import br.com.dbug.questlab.service.BancaService;
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
@RequestMapping("/api/bancas")
@RequiredArgsConstructor
@Tag(name = "Bancas", description = "Gerenciamento de bancas organizadoras")

public class BancaController {

    private final BancaService bancaService;

    @PostMapping
    @Operation(summary = "Criar nova banca")
    public ResponseEntity<BancaResponseDTO> create(@Valid @RequestBody BancaRequestDTO request) {
        log.info("Criando nova banca: {}", request.getSigla());
        BancaResponseDTO response = bancaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar banca por ID")
    public ResponseEntity<BancaResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando banca ID: {}", id);
        BancaResponseDTO response = bancaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as bancas")
    public ResponseEntity<List<BancaResponseDTO>> findAll() {
        log.info("Listando todas as bancas");
        List<BancaResponseDTO> response = bancaService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar bancas com paginação e filtros")
    public ResponseEntity<Page<BancaResponseDTO>> findAllPaginated(
            @ModelAttribute BancaFilterDTO filter) {
        log.info("Listando bancas com filtros");
        Page<BancaResponseDTO> response = bancaService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar banca")
    public ResponseEntity<BancaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody BancaRequestDTO request) {
        log.info("Atualizando banca ID: {}", id);
        BancaResponseDTO response = bancaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir banca")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo banca ID: {}", id);
        bancaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar banca")
    public ResponseEntity<Void> inactivate(@PathVariable Integer id) {
        log.info("Inativando banca ID: {}", id);
        bancaService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar banca")
    public ResponseEntity<Void> activate(@PathVariable Integer id) {
        log.info("Ativando banca ID: {}", id);
        bancaService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
