package br.com.dbug.questlab.rest.controller;

import br.com.dbug.questlab.rest.dto.filter.UsuarioFilterDTO;
import br.com.dbug.questlab.rest.dto.request.UsuarioRequestDTO;
import br.com.dbug.questlab.rest.dto.response.UsuarioResponseDTO;
import br.com.dbug.questlab.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO request) {
        log.info("Criando novo usuário: {}", request.getEmail());
        UsuarioResponseDTO response = usuarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Integer id) {
        log.info("Buscando usuário ID: {}", id);
        UsuarioResponseDTO response = usuarioService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        log.info("Listando todos os usuários");
        List<UsuarioResponseDTO> response = usuarioService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Listar usuários com paginação e filtros")
    public ResponseEntity<Page<UsuarioResponseDTO>> findAllPaginated(
            @ModelAttribute UsuarioFilterDTO filter) {
        log.info("Listando usuários com filtros - página: {}, tamanho: {}",
                filter.getPage(), filter.getSize());
        Page<UsuarioResponseDTO> response = usuarioService.findAllPaginated(filter);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDTO request) {
        log.info("Atualizando usuário ID: {}", id);
        UsuarioResponseDTO response = usuarioService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Excluindo usuário ID: {}", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar usuário")
    public ResponseEntity<Void> inactivate(@PathVariable Integer id) {
        log.info("Inativando usuário ID: {}", id);
        usuarioService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar usuário")
    public ResponseEntity<Void> activate(@PathVariable Integer id) {
        log.info("Ativando usuário ID: {}", id);
        usuarioService.activate(id);
        return ResponseEntity.noContent().build();
    }
}