package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.CargoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.CargoResponseDTO;
import org.springframework.stereotype.Service;

@Service

public interface CargoService extends CrudService<Integer, CargoRequestDTO, CargoResponseDTO> {
}