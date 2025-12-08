package br.com.dbug.questlab.service;

import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.rest.dto.request.ProvaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.ProvaResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProvaService extends CrudService<Integer, ProvaRequestDTO, ProvaResponseDTO, ProvaModel> {
}