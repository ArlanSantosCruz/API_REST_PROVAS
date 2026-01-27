package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.model.ConcursoModel;
import br.com.dbug.questlab.model.InstituicaoModel;
import br.com.dbug.questlab.repository.BancaRepository;
import br.com.dbug.questlab.repository.ConcursoRepository;
import br.com.dbug.questlab.repository.InstituicaoRepository;
import br.com.dbug.questlab.rest.dto.request.ConcursoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.ConcursoResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.BancaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.InstituicaoIdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import br.com.dbug.questlab.rest.dto.filter.ConcursoFilterDTO;
import br.com.dbug.questlab.rest.dto.response.ConcursoResponseDTO;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcursoService {

    private final ConcursoRepository repository;
    private final BancaRepository bancaRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ConcursoResponseDTO create(ConcursoRequestDTO request) {
        log.info("Criando concurso: {}", request.getNome());

        if (repository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe um concurso com o nome: " + request.getNome());
        }

        // Buscar banca
        BancaModel banca = bancaRepository.findById(request.getBanca().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada"));

        // Buscar instituição
        InstituicaoModel instituicao = instituicaoRepository.findById(request.getInstituicao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada"));

        ConcursoModel entity = new ConcursoModel();
        entity.setNome(request.getNome());
        entity.setAno(request.getAno());
        entity.setCancelado(request.getCancelado());
        entity.setAtivo(request.getAtivo());
        entity.setBanca(banca);
        entity.setInstituicao(instituicao);

        ConcursoModel saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public ConcursoResponseDTO findById(Integer id) {
        log.debug("Buscando concurso ID: {}", id);

        ConcursoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));

        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ConcursoResponseDTO> findAll() {
        log.debug("Listando todos os concursos");

        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ConcursoResponseDTO update(Integer id, ConcursoRequestDTO request) {
        log.info("Atualizando concurso ID: {}", id);

        ConcursoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));

        if (!entity.getNome().equals(request.getNome()) && repository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe outro concurso com o nome: " + request.getNome());
        }

        // Buscar banca e instituição
        BancaModel banca = bancaRepository.findById(request.getBanca().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada"));
        InstituicaoModel instituicao = instituicaoRepository.findById(request.getInstituicao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada"));

        entity.setNome(request.getNome());
        entity.setAno(request.getAno());
        entity.setCancelado(request.getCancelado());
        entity.setAtivo(request.getAtivo());
        entity.setBanca(banca);
        entity.setInstituicao(instituicao);

        ConcursoModel updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo concurso ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Concurso não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void cancelar(Integer id) {
        ConcursoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));
        entity.setCancelado(true);
        repository.save(entity);
    }

    @Transactional
    public void ativar(Integer id) {
        ConcursoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));
        entity.setAtivo(true);
        repository.save(entity);
    }

    @Transactional
    public void inativar(Integer id) {
        ConcursoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }

    private ConcursoResponseDTO toResponseDTO(ConcursoModel entity) {
        ConcursoResponseDTO dto = new ConcursoResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setAno(entity.getAno());
        dto.setCancelado(entity.getCancelado());
        dto.setAtivo(entity.getAtivo());

        BancaIdDTO bancaDTO = new BancaIdDTO();
        bancaDTO.setId(entity.getBanca().getId());
        bancaDTO.setRazaoSocial(entity.getBanca().getNome());
        bancaDTO.setSigla(entity.getBanca().getSigla());
        dto.setBanca(bancaDTO);

        InstituicaoIdDTO instituicaoDTO = new InstituicaoIdDTO();
        instituicaoDTO.setId(entity.getInstituicao().getId());
        instituicaoDTO.setRazaoSocial(entity.getInstituicao().getRazaoSocial());
        instituicaoDTO.setSigla(entity.getInstituicao().getSigla());
        dto.setInstituicao(instituicaoDTO);

        return dto;
    }
    public Page<ConcursoResponseDTO> findAllPaginated(ConcursoFilterDTO filter) {
        log.info("Listando concursos com filtros e paginação");

        // Configura a ordenação
        Sort sort = Sort.by(
                filter.getSortDirection() != null && filter.getSortDirection().equalsIgnoreCase("DESC")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                filter.getSortBy() != null ? filter.getSortBy() : "id"
        );

        // Cria o objeto Pageable
        Pageable pageable = PageRequest.of(
                filter.getPage() != null ? filter.getPage() : 0,
                filter.getSize() != null ? filter.getSize() : 10,
                sort
        );

        // Busca todos
        Page<ConcursoModel> page = repository.findAll(pageable);

        // Converte para DTO
        return page.map(concurso -> modelMapper.map(concurso, ConcursoResponseDTO.class));
    }



}