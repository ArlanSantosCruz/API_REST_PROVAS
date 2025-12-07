package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.InstituicaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.InstituicaoResponseDTO;
import org.springframework.stereotype.Service;

@Service

public interface InstituicaoService extends CrudService<Integer, InstituicaoRequestDTO, InstituicaoResponseDTO> {
    void inactivate(Integer id);
    void activate(Integer id);
}