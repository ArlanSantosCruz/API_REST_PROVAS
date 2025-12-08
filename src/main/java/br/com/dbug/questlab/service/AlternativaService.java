package br.com.dbug.questlab.service;

import br.com.dbug.questlab.model.AlternativaModel;
import br.com.dbug.questlab.rest.dto.request.AlternativaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AlternativaResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AlternativaService extends CrudService<Integer, AlternativaRequestDTO, AlternativaResponseDTO, AlternativaModel> {
}

