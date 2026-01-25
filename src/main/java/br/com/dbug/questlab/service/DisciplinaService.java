package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.DisciplinaModel;
import br.com.dbug.questlab.repository.DisciplinaRepository;
import br.com.dbug.questlab.rest.dto.request.DisciplinaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.DisciplinaResponseDTO;
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
public class DisciplinaService {

    private final DisciplinaRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public DisciplinaResponseDTO create(DisciplinaRequestDTO request) {
        log.info("Criando disciplina: {}", request.getNome());

        if (repository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe uma disciplina com o nome: " + request.getNome());
        }

        DisciplinaModel entity = modelMapper.map(request, DisciplinaModel.class);
        DisciplinaModel saved = repository.save(entity);
        return modelMapper.map(saved, DisciplinaResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public DisciplinaResponseDTO findById(Integer id) {
        log.debug("Buscando disciplina ID: {}", id);

        DisciplinaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + id));

        return modelMapper.map(entity, DisciplinaResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResponseDTO> findAll() {
        log.debug("Listando todas as disciplinas");

        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, DisciplinaResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DisciplinaResponseDTO update(Integer id, DisciplinaRequestDTO request) {
        log.info("Atualizando disciplina ID: {}", id);

        DisciplinaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + id));

        if (!entity.getNome().equals(request.getNome()) && repository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe outra disciplina com o nome: " + request.getNome());
        }

        entity.setNome(request.getNome());

        DisciplinaModel updated = repository.save(entity);
        return modelMapper.map(updated, DisciplinaResponseDTO.class);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo disciplina ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Disciplina não encontrada com ID: " + id);
        }

        repository.deleteById(id);
    }
}
