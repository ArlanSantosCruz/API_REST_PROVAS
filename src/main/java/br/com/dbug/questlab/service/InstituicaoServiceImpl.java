package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.InstituicaoRequestDTO;
import br.com.dbug.questlab.dto.response.InstituicaoResponseDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.InstituicaoModel;
import br.com.dbug.questlab.repository.InstituicaoRepository;
import br.com.dbug.questlab.service.InstituicaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InstituicaoServiceImpl extends CrudServiceImpl<InstituicaoModel, Integer, InstituicaoRequestDTO, InstituicaoResponseDTO>
        implements InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoServiceImpl(InstituicaoRepository instituicaoRepository, org.modelmapper.ModelMapper modelMapper) {
        super(instituicaoRepository, modelMapper, InstituicaoModel.class, InstituicaoResponseDTO.class, "Instituição");
        this.instituicaoRepository = instituicaoRepository;
    }

    @Override
    protected void beforeCreate(InstituicaoModel entity, InstituicaoRequestDTO request) {
        // Verificar CNPJ único
        instituicaoRepository.findByCnpj(request.getCnpj())
                .ifPresent(i -> {
                    throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
                });

        // Verificar sigla única
        instituicaoRepository.findBySigla(request.getSigla())
                .ifPresent(i -> {
                    throw new BusinessException("Sigla já cadastrada: " + request.getSigla());
                });
    }

    @Override
    protected void beforeUpdate(InstituicaoModel entity, InstituicaoRequestDTO request) {
        // Verificar CNPJ único (se alterado)
        if (!entity.getCnpj().equals(request.getCnpj())) {
            instituicaoRepository.findByCnpj(request.getCnpj())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
                        }
                    });
        }

        // Verificar sigla única (se alterada)
        if (!entity.getSigla().equals(request.getSigla())) {
            instituicaoRepository.findBySigla(request.getSigla())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("Sigla já cadastrada: " + request.getSigla());
                        }
                    });
        }
    }

    @Override
    @Transactional
    public void inactivate(Integer id) {
        log.info("Inativando instituição ID: {}", id);

        InstituicaoModel instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));

        instituicao.setAtivo(false);
        instituicaoRepository.save(instituicao);

        log.info("Instituição ID: {} inativada", id);
    }

    @Override
    @Transactional
    public void activate(Integer id) {
        log.info("Ativando instituição ID: {}", id);

        InstituicaoModel instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));

        instituicao.setAtivo(true);
        instituicaoRepository.save(instituicao);

        log.info("Instituição ID: {} ativada", id);
    }

    @Override
    protected Integer getId(InstituicaoModel entity) {
        return entity.getId();
    }
}
