package br.com.dbug.questlab.service;

import br.com.dbug.questlab.model.ConcursoModel;
import br.com.dbug.questlab.rest.dto.request.ConcursoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.ConcursoResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ConcursoService extends CrudService<ConcursoModel, Integer, ConcursoRequestDTO, ConcursoResponseDTO> {
    void cancelar(Integer id);
    void ativar(Integer id);
    void inativar(Integer id);
}