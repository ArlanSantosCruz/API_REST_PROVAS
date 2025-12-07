package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.UsuarioRespostaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.UsuarioRespostaResponseDTO;
import org.springframework.stereotype.Service;

@Service

public interface UsuarioRespostaService extends CrudService<Integer, UsuarioRespostaRequestDTO, UsuarioRespostaResponseDTO> {
}
