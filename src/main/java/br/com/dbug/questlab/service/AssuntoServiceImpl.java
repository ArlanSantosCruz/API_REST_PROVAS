package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.AssuntoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AssuntoResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.DisciplinaIdDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AssuntoModel;
import br.com.dbug.questlab.model.DisciplinaModel;
import br.com.dbug.questlab.repository.AssuntoRepository;
import br.com.dbug.questlab.repository.DisciplinaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AssuntoServiceImpl extends CrudServiceImpl<AssuntoModel, Integer, AssuntoRequestDTO, AssuntoResponseDTO>
        implements AssuntoService {

    private final AssuntoRepository assuntoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public AssuntoServiceImpl(AssuntoRepository assuntoRepository,
                              DisciplinaRepository disciplinaRepository,
                              ModelMapper modelMapper) {
        super(assuntoRepository, modelMapper, AssuntoModel.class, AssuntoResponseDTO.class, "Assunto");
        this.assuntoRepository = assuntoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    protected void beforeCreate(AssuntoModel entity, AssuntoRequestDTO request) {
        // Validar disciplina
        DisciplinaModel disciplina = disciplinaRepository.findById(request.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + request.getDisciplina().getId()));

        entity.setDisciplina(disciplina);

        // Verificar nome único por disciplina
        if (assuntoRepository.existsByNomeAndDisciplinaId(request.getNome(), request.getDisciplina().getId())) {
            throw new BusinessException("Já existe um assunto com este nome nesta disciplina");
        }
    }

    @Override
    protected void beforeUpdate(AssuntoModel entity, AssuntoRequestDTO request) {
        // Atualizar disciplina se necessário
        if (!entity.getDisciplina().getId().equals(request.getDisciplina().getId())) {
            DisciplinaModel disciplina = disciplinaRepository.findById(request.getDisciplina().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + request.getDisciplina().getId()));
            entity.setDisciplina(disciplina);
        }

        // Verificar nome único por disciplina
        if (!entity.getNome().equals(request.getNome()) ||
                !entity.getDisciplina().getId().equals(request.getDisciplina().getId())) {

            assuntoRepository.findByNomeAndDisciplinaId(request.getNome(), request.getDisciplina().getId())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("Já existe outro assunto com este nome nesta disciplina");
                        }
                    });
        }
    }

    @Override
    protected AssuntoResponseDTO toResponse(AssuntoModel entity) {
        AssuntoResponseDTO response = modelMapper.map(entity, AssuntoResponseDTO.class);

        DisciplinaIdDTO disciplinaDTO = new DisciplinaIdDTO();
        disciplinaDTO.setId(entity.getDisciplina().getId());
        disciplinaDTO.setNome(entity.getDisciplina().getNome());
        response.setDisciplina(disciplinaDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssuntoResponseDTO> findByDisciplinaId(Integer disciplinaId) {
        log.debug("Buscando assuntos por disciplina ID: {}", disciplinaId);

        return assuntoRepository.findByDisciplinaId(disciplinaId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer getId(AssuntoModel entity) {
        return entity.getId();
    }
}
