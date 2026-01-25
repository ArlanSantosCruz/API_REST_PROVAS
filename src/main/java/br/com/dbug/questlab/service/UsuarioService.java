package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.UsuarioModel;
import br.com.dbug.questlab.repository.UsuarioRepository;
import br.com.dbug.questlab.rest.dto.request.UsuarioRequestDTO;
import br.com.dbug.questlab.rest.dto.response.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO request) {
        log.info("Criando usuário: {}", request.getEmail());

        // Validações
        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + request.getEmail());
        }
        if (repository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + request.getCpf());
        }

        // Converter DTO para Model
        UsuarioModel entity = modelMapper.map(request, UsuarioModel.class);

        // Criptografar senha
        entity.setSenha(passwordEncoder.encode(request.getSenha()));

        // Salvar
        UsuarioModel saved = repository.save(entity);

        // Converter Model para DTO de resposta
        return modelMapper.map(saved, UsuarioResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Integer id) {
        log.debug("Buscando usuário ID: {}", id);

        UsuarioModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        return modelMapper.map(entity, UsuarioResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        log.debug("Listando todos os usuários");

        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDTO update(Integer id, UsuarioRequestDTO request) {
        log.info("Atualizando usuário ID: {}", id);

        UsuarioModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // Validações
        if (!entity.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + request.getEmail());
        }
        if (!entity.getCpf().equals(request.getCpf()) && repository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + request.getCpf());
        }

        // Atualizar campos
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setCpf(request.getCpf());
        entity.setDataNascimento(request.getDataNascimento());
        entity.setSexo(request.getSexo());
        entity.setPerfil(request.getPerfil());
        entity.setCelular(request.getCelular());
        entity.setTelefone(request.getTelefone());
        entity.setAtivo(request.getAtivo());

        UsuarioModel updated = repository.save(entity);
        return modelMapper.map(updated, UsuarioResponseDTO.class);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo usuário ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void inactivate(Integer id) {
        UsuarioModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }

    @Transactional
    public void activate(Integer id) {
        UsuarioModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        entity.setAtivo(true);
        repository.save(entity);
    }
}