package br.com.dbug.questlab.service;

import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.rest.dto.request.QuestaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.QuestaoResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface QuestaoService extends CrudService<Integer, QuestaoRequestDTO, QuestaoResponseDTO, QuestaoModel> {
    void anular(Integer id, String motivo);
    void reativar(Integer id);
}