package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.AlternativaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AlternativaResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AlternativaModel;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.repository.AlternativaRepository;
import br.com.dbug.questlab.repository.QuestaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlternativaServiceImpl extends CrudServiceImpl<AlternativaModel, Integer, AlternativaRequestDTO, AlternativaResponseDTO>
        implements AlternativaService {

    private final AlternativaRepository alternativaRepository;
    private final QuestaoRepository questaoRepository;

    public AlternativaServiceImpl(AlternativaRepository alternativaRepository,
                                  QuestaoRepository questaoRepository,
                                  ModelMapper modelMapper) {
        super(alternativaRepository, modelMapper, AlternativaModel.class, AlternativaResponseDTO.class, "Alternativa");
        this.alternativaRepository = alternativaRepository;
        this.questaoRepository = questaoRepository;
    }

    @Override
    protected void beforeCreate(AlternativaModel entity, AlternativaRequestDTO request) {
        // Buscar questão
        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Questão não encontrada com ID: " + request.getQuestao().getId()));
        entity.setQuestao(questao);

        // Verificar se questão está anulada
        if (questao.getAnulada()) {
            throw new BusinessException("Não é possível adicionar alternativa a uma questão anulada");
        }

        // Verificar se já existe alternativa correta para esta questão
        if (request.getCorreta()) {
            List<AlternativaModel> alternativasCorretas = alternativaRepository.findByCorretaTrue();
            alternativasCorretas.stream()
                    .filter(a -> a.getQuestao().getId().equals(questao.getId()))
                    .findFirst()
                    .ifPresent(a -> {
                        throw new BusinessException("Já existe uma alternativa correta para esta questão");
                    });
        }
    }

    @Override
    protected void beforeUpdate(AlternativaModel entity, AlternativaRequestDTO request) {
        // Buscar questão
        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Questão não encontrada com ID: " + request.getQuestao().getId()));

        // Verificar se questão está anulada
        if (questao.getAnulada()) {
            throw new BusinessException("Não é possível alterar alternativa de uma questão anulada");
        }

        // Se está marcando como correta, verificar se já existe outra correta
        if (request.getCorreta() && !entity.getCorreta()) {
            List<AlternativaModel> alternativasCorretas = alternativaRepository.findByCorretaTrue();
            alternativasCorretas.stream()
                    .filter(a -> a.getQuestao().getId().equals(questao.getId()) && !a.getId().equals(entity.getId()))
                    .findFirst()
                    .ifPresent(a -> {
                        throw new BusinessException("Já existe uma alternativa correta para esta questão");
                    });
        }

        entity.setQuestao(questao);
    }

    @Override
    protected AlternativaResponseDTO toResponse(AlternativaModel entity) {
        AlternativaResponseDTO response = modelMapper.map(entity, AlternativaResponseDTO.class);

        QuestaoIdDTO questaoDTO = new QuestaoIdDTO();
        questaoDTO.setId(entity.getQuestao().getId());
        questaoDTO.setEnunciadoResumido(
                entity.getQuestao().getEnunciado().length() > 50 ?
                        entity.getQuestao().getEnunciado().substring(0, 50) + "..." :
                        entity.getQuestao().getEnunciado());
        questaoDTO.setAnulada(entity.getQuestao().getAnulada());
        response.setQuestao(questaoDTO);

        return response;
    }

    @Override
    protected Integer getId(AlternativaModel entity) {
        return entity.getId();
    }
}