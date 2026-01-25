package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AssuntoModel;
import br.com.dbug.questlab.model.DisciplinaModel;
import br.com.dbug.questlab.repository.AssuntoRepository;
import br.com.dbug.questlab.repository.DisciplinaRepository;
import br.com.dbug.questlab.rest.dto.request.AssuntoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AssuntoResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.DisciplinaIdDTO;
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
public class AssuntoService {

    private final AssuntoRepository repository;
    private final DisciplinaRepository disciplinaRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AssuntoResponseDTO create(AssuntoRequestDTO request) {
        log.info("Criando assunto: {}", request.getNome());

        DisciplinaModel disciplina = disciplinaRepository.findById(request.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        if (repository.existsByNomeAndDisciplinaId(request.getNome(), disciplina.getId())) {
            throw new BusinessException("Já existe um assunto com este nome nesta disciplina");
        }

        AssuntoModel entity = new AssuntoModel();
        entity.setNome(request.getNome());
        entity.setDisciplina(disciplina);

        AssuntoModel saved = repository.save(entity);
        return toResponseDTO(saved);
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

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo assunto ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Assunto não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    private AssuntoResponseDTO toResponseDTO(AssuntoModel entity) {
        AssuntoResponseDTO dto = new AssuntoResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        DisciplinaIdDTO disciplinaDTO = new DisciplinaIdDTO();
        disciplinaDTO.setId(entity.getDisciplina().getId());
        disciplinaDTO.setNome(entity.getDisciplina().getNome());
        dto.setDisciplina(disciplinaDTO);

        return dto;
    }
}