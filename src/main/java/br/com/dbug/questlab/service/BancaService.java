package br.com.dbug.questlab.service;

import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.rest.dto.request.BancaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.BancaResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface BancaService extends CrudService<Integer, BancaRequestDTO, BancaResponseDTO, BancaModel> {
    void inactivate(Integer id);
    void activate(Integer id);
}