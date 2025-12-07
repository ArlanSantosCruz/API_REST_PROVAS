package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.AssuntoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AssuntoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AssuntoService extends CrudService<Integer, AssuntoRequestDTO, AssuntoResponseDTO> {
    List<AssuntoResponseDTO> findByDisciplinaId(Integer disciplinaId);
}