package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.InstituicaoModel;
import br.com.dbug.questlab.repository.InstituicaoRepository;
import br.com.dbug.questlab.rest.dto.request.InstituicaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.InstituicaoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstituicaoService {

    private final InstituicaoRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public InstituicaoResponseDTO create(InstituicaoRequestDTO request) {
        log.info("Criando instituição: {}", request.getSigla());

        if (repository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
        }
        if (repository.existsBySigla(request.getSigla())) {
            throw new BusinessException("Sigla já cadastrada: " + request.getSigla());
        }

        InstituicaoModel entity = modelMapper.map(request, InstituicaoModel.class);
        InstituicaoModel saved = repository.save(entity);
        return modelMapper.map(saved, InstituicaoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public InstituicaoResponseDTO findById(Integer id) {
        log.debug("Buscando instituição ID: {}", id);

        InstituicaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));

        return modelMapper.map(entity, InstituicaoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<InstituicaoResponseDTO> findAll() {
        log.debug("Listando todas as instituições");

        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, InstituicaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public InstituicaoResponseDTO update(Integer id, InstituicaoRequestDTO request) {
        log.info("Atualizando instituição ID: {}", id);

        InstituicaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));

        if (!entity.getCnpj().equals(request.getCnpj()) && repository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
        }
        if (!entity.getSigla().equals(request.getSigla()) && repository.existsBySigla(request.getSigla())) {
            throw new BusinessException("Sigla já cadastrada: " + request.getSigla());
        }

        entity.setRazaoSocial(request.getRazaoSocial());
        entity.setSigla(request.getSigla());
        entity.setCnpj(request.getCnpj());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setAtivo(request.getAtivo());

        InstituicaoModel updated = repository.save(entity);
        return modelMapper.map(updated, InstituicaoResponseDTO.class);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo instituição ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Instituição não encontrada com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void inactivate(Integer id) {
        InstituicaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }

    @Transactional
    public void activate(Integer id) {
        InstituicaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));
        entity.setAtivo(true);
        repository.save(entity);
    }
}