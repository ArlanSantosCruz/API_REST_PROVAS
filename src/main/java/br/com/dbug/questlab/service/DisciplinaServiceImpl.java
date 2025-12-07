package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.DisciplinaRequestDTO;
import br.com.dbug.questlab.dto.response.DisciplinaResponseDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.model.DisciplinaModel;
import br.com.dbug.questlab.repository.DisciplinaRepository;
import br.com.dbug.questlab.service.DisciplinaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DisciplinaServiceImpl extends CrudServiceImpl<DisciplinaModel, Integer, DisciplinaRequestDTO, DisciplinaResponseDTO>
        implements DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository, org.modelmapper.ModelMapper modelMapper) {
        super(disciplinaRepository, modelMapper, DisciplinaModel.class, DisciplinaResponseDTO.class, "Disciplina");
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    protected void beforeCreate(DisciplinaModel entity, DisciplinaRequestDTO request) {
        if (disciplinaRepository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe uma disciplina com o nome: " + request.getNome());
        }
    }

    @Override
    protected void beforeUpdate(DisciplinaModel entity, DisciplinaRequestDTO request) {
        if (!entity.getNome().equals(request.getNome())) {
            disciplinaRepository.findByNome(request.getNome())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("Já existe outra disciplina com o nome: " + request.getNome());
                        }
                    });
        }
    }

    @Override
    protected Integer getId(DisciplinaModel entity) {
        return entity.getId();
    }
}
