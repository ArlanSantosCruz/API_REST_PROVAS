package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AssuntoModel;
import br.com.dbug.questlab.model.DisciplinaModel;
import br.com.dbug.questlab.repository.AssuntoRepository;
import br.com.dbug.questlab.repository.DisciplinaRepository;
import br.com.dbug.questlab.rest.dto.filter.AssuntoFilterDTO;
import br.com.dbug.questlab.rest.dto.request.AssuntoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AssuntoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository repository;
    private final DisciplinaRepository disciplinaRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<AssuntoResponseDTO> findByDisciplinaId(Integer disciplinaId) {
        log.info("Listando assuntos da disciplina ID: {}", disciplinaId);

        List<AssuntoModel> assuntos = repository.findByDisciplinaId(disciplinaId);

        return assuntos.stream()
                .map(assunto -> modelMapper.map(assunto, AssuntoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Page<AssuntoResponseDTO> findAllPaginated(AssuntoFilterDTO filter) {
        log.info("Listando assuntos com filtros");

        // Configura a ordenação
        Sort sort = Sort.by(
                filter.getSortDirection().equalsIgnoreCase("DESC")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                filter.getSortBy()
        );

        // Cria o objeto Pageable
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        // Busca com ou sem filtro de disciplina
        Page<AssuntoModel> page;
        if (filter.getDisciplinaId() != null) {
            page = repository.findByDisciplinaId(filter.getDisciplinaId(), pageable);
        } else if (filter.getNome() != null && !filter.getNome().isEmpty()) {
            page = repository.findByNomeContainingIgnoreCase(filter.getNome(), pageable);
        } else {
            page = repository.findAll(pageable);
        }

        // Converte para DTO
        return page.map(assunto -> modelMapper.map(assunto, AssuntoResponseDTO.class));
    }

    @Transactional(readOnly = true)
    public AssuntoResponseDTO findById(Integer id) {
        log.debug("Buscando assunto ID: {}", id);

        AssuntoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado com ID: " + id));

        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<AssuntoResponseDTO> findAll() {
        log.debug("Listando todos os assuntos");

        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AssuntoResponseDTO update(Integer id, AssuntoRequestDTO request) {
        log.info("Atualizando assunto ID: {}", id);

        AssuntoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado com ID: " + id));

        DisciplinaModel disciplina = disciplinaRepository.findById(request.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        if (!entity.getNome().equals(request.getNome()) &&
                repository.existsByNomeAndDisciplinaId(request.getNome(), disciplina.getId())) {
            throw new BusinessException("Já existe outro assunto com este nome nesta disciplina");
        }

        entity.setNome(request.getNome());
        entity.setDisciplina(disciplina);

        AssuntoModel updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    private AssuntoResponseDTO toResponseDTO(AssuntoModel updated) {
        return null;
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo assunto ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Assunto não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public AssuntoResponseDTO create(AssuntoRequestDTO request) {
        log.info("Criando novo assunto: {}", request.getNome());

        // Cria a entidade
        AssuntoModel entity = new AssuntoModel();
        entity.setNome(request.getNome());

        // Se o request tiver disciplinaId, busque e associe a disciplina
        if (request.getDisciplinaId() != null) {
            DisciplinaModel disciplina = disciplinaRepository.findById(request.getDisciplinaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + request.getDisciplinaId()));
            entity.setDisciplina(disciplina);
        }

        // Salva no banco
        AssuntoModel saved = repository.save(entity);

        // Converte para DTO e retorna
        return modelMapper.map(saved, AssuntoResponseDTO.class);
    }
}