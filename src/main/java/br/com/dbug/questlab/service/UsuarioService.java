package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.UsuarioRequestDTO;
import br.com.dbug.questlab.rest.dto.response.UsuarioResponseDTO;
import org.springframework.stereotype.Service;

@Service

public interface UsuarioService extends CrudService<Integer, UsuarioRequestDTO, UsuarioResponseDTO> {
    void inactivate(Integer id);
    void activate(Integer id);
}