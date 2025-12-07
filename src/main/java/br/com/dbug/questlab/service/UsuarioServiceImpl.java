package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.UsuarioRequestDTO;
import br.com.dbug.questlab.dto.response.UsuarioResponseDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.UsuarioModel;
import br.com.dbug.questlab.repository.UsuarioRepository;
import br.com.dbug.questlab.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UsuarioServiceImpl extends CrudServiceImpl<UsuarioModel, Integer, UsuarioRequestDTO, UsuarioResponseDTO>
        implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder,
                              org.modelmapper.ModelMapper modelMapper) {
        super(usuarioRepository, modelMapper, UsuarioModel.class, UsuarioResponseDTO.class, "Usuário");
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void beforeCreate(UsuarioModel entity, UsuarioRequestDTO request) {
        // Verificar email único
        usuarioRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new BusinessException("Email já cadastrado: " + request.getEmail());
                });

        // Verificar CPF único
        usuarioRepository.findByCpf(request.getCpf())
                .ifPresent(u -> {
                    throw new BusinessException("CPF já cadastrado: " + request.getCpf());
                });

        // Validar perfil
        if (!isPerfilValido(request.getPerfil())) {
            throw new BusinessException("Perfil inválido: " + request.getPerfil() +
                    ". Perfis válidos: ADMIN, PROFESSOR, ALUNO");
        }

        // Criptografar senha
        entity.setSenha(passwordEncoder.encode(request.getSenha()));
    }

    @Override
    protected void beforeUpdate(UsuarioModel entity, UsuarioRequestDTO request) {
        // Verificar email único (se alterado)
        if (!entity.getEmail().equals(request.getEmail())) {
            usuarioRepository.findByEmail(request.getEmail())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("Email já cadastrado: " + request.getEmail());
                        }
                    });
        }

        // Verificar CPF único (se alterado)
        if (!entity.getCpf().equals(request.getCpf())) {
            usuarioRepository.findByCpf(request.getCpf())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("CPF já cadastrado: " + request.getCpf());
                        }
                    });
        }

        // Validar perfil
        if (!isPerfilValido(request.getPerfil())) {
            throw new BusinessException("Perfil inválido: " + request.getPerfil());
        }

        // Não atualizar senha através do update normal
        // Para atualizar senha, usar método específico
    }

    @Override
    protected UsuarioResponseDTO toResponse(UsuarioModel entity) {
        UsuarioResponseDTO response = modelMapper.map(entity, UsuarioResponseDTO.class);
        // Garantir que a senha não seja exposta
        response.setSenha(null);
        return response;
    }

    @Override
    @Transactional
    public void inactivate(Integer id) {
        log.info("Inativando usuário ID: {}", id);

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);

        log.info("Usuário ID: {} inativado", id);
    }

    @Override
    @Transactional
    public void activate(Integer id) {
        log.info("Ativando usuário ID: {}", id);

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuario.setAtivo(true);
        usuarioRepository.save(usuario);

        log.info("Usuário ID: {} ativado", id);
    }

    @Override
    protected Integer getId(UsuarioModel entity) {
        return entity.getId();
    }

    private boolean isPerfilValido(String perfil) {
        return perfil != null &&
                (perfil.equalsIgnoreCase("ADMIN") ||
                        perfil.equalsIgnoreCase("PROFESSOR") ||
                        perfil.equalsIgnoreCase("ALUNO"));
    }
}
