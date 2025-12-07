package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.CargoFilterDTO;
import br.com.dbug.questlab.rest.dto.request.CargoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.CargoResponseDTO;
import br.com.dbug.questlab.service.CargoService;
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
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
@Tag(name = "Cargos", description = "Gerenciamento de cargos")
public class CargoController {

    private final CargoService cargoService;

    @PostMapping
    @Operation(summary = "Criar novo cargo")
    public ResponseEntity<CargoResponseDTO> create(@Valid @RequestBody CargoRequestDTO request) {
        log.info("Criando novo cargo: {}", request.getNome());
        CargoResponseDTO response = cargoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cargo por ID")
    public ResponseEntity<CargoResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando cargo ID: {}", id);
        CargoResponseDTO response = cargoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os cargos")
    public ResponseEntity<List<CargoResponseDTO>> findAll() {
        log.info("Listando todos os cargos");
        List<CargoResponseDTO> response = cargoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar cargos com paginação e filtros")
    public ResponseEntity<Page<CargoResponseDTO>> findAllPaginated(
            @ModelAttribute CargoFilterDTO filter) {
        log.info("Listando cargos com filtros");
        Page<CargoResponseDTO> response = cargoService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cargo")
    public ResponseEntity<CargoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CargoRequestDTO request) {
        log.info("Atualizando cargo ID: {}", id);
        CargoResponseDTO response = cargoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cargo")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo cargo ID: {}", id);
        cargoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}