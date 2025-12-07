package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.DisciplinaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.DisciplinaResponseDTO;
import org.springframework.stereotype.Service;

@Service

public interface DisciplinaService extends CrudService<Integer, DisciplinaRequestDTO, DisciplinaResponseDTO> {
}
