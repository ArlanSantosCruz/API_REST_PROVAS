package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.repository.BancaRepository;
import br.com.dbug.questlab.rest.dto.request.BancaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.BancaResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import br.com.dbug.questlab.rest.dto.filter.BancaFilterDTO;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BancaService {

    private final BancaRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public BancaResponseDTO create(BancaRequestDTO request) {
        log.info("Criando banca: {}", request.getSigla());

        if (repository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
        }
        if (repository.existsBySigla(request.getSigla())) {
            throw new BusinessException("Sigla já cadastrada: " + request.getSigla());
        }

        BancaModel entity = modelMapper.map(request, BancaModel.class);
        BancaModel saved = repository.save(entity);
        return modelMapper.map(saved, BancaResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public BancaResponseDTO findById(Integer id) {
        log.debug("Buscando banca ID: {}", id);

        BancaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com ID: " + id));

        return modelMapper.map(entity, BancaResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<BancaResponseDTO> findAll() {
        log.debug("Listando todas as bancas");

        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, BancaResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public BancaResponseDTO update(Integer id, BancaRequestDTO request) {
        log.info("Atualizando banca ID: {}", id);

        BancaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com ID: " + id));

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

        BancaModel updated = repository.save(entity);
        return modelMapper.map(updated, BancaResponseDTO.class);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo banca ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Banca não encontrada com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void inactivate(Integer id) {
        BancaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com ID: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }

    @Transactional
    public void activate(Integer id) {
        BancaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com ID: " + id));
        entity.setAtivo(true);
        repository.save(entity);
    }
    @Transactional(readOnly = true)
    public Page<BancaResponseDTO> findAllPaginated(BancaFilterDTO filter) {
        log.info("Listando bancas com filtros e paginação");

        Sort sort = Sort.by(
                filter.getSortDirection() != null && filter.getSortDirection().equalsIgnoreCase("DESC")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                filter.getSortBy() != null ? filter.getSortBy() : "id"
        );

        Pageable pageable = PageRequest.of(
                filter.getPage() != null ? filter.getPage() : 0,
                filter.getSize() != null ? filter.getSize() : 10,
                sort
        );

        Page<BancaModel> page = repository.findAll(pageable);

        return page.map(banca -> modelMapper.map(banca, BancaResponseDTO.class));
    }
}
