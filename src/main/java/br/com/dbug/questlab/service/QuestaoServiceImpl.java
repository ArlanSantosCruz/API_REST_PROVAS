package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.QuestaoRequestDTO;
import br.com.dbug.questlab.dto.response.QuestaoResponseDTO;
import br.com.dbug.questlab.dto.simplified.AssuntoIdDTO;
import br.com.dbug.questlab.dto.simplified.ProvaIdDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AssuntoModel;
import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.repository.AssuntoRepository;
import br.com.dbug.questlab.repository.ProvaRepository;
import br.com.dbug.questlab.repository.QuestaoRepository;
import br.com.dbug.questlab.service.QuestaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class QuestaoServiceImpl extends CrudServiceImpl<QuestaoModel, Integer, QuestaoRequestDTO, QuestaoResponseDTO>
        implements QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final ProvaRepository provaRepository;
    private final AssuntoRepository assuntoRepository;

    public QuestaoServiceImpl(QuestaoRepository questaoRepository,
                              ProvaRepository provaRepository,
                              AssuntoRepository assuntoRepository,
                              org.modelmapper.ModelMapper modelMapper) {
        super(questaoRepository, modelMapper, QuestaoModel.class, QuestaoResponseDTO.class, "Questão");
        this.questaoRepository = questaoRepository;
        this.provaRepository = provaRepository;
        this.assuntoRepository = assuntoRepository;
    }

    @Override
    protected void beforeCreate(QuestaoModel entity, QuestaoRequestDTO request) {
        // Buscar prova
        ProvaModel prova = provaRepository.findById(request.getProva().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prova não encontrada com ID: " + request.getProva().getId()));
        entity.setProva(prova);

        // Buscar assunto
        AssuntoModel assunto = assuntoRepository.findById(request.getAssunto().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assunto não encontrado com ID: " + request.getAssunto().getId()));
        entity.setAssunto(assunto);

        entity.setAnulada(false);
    }

    @Override
    protected void beforeUpdate(QuestaoModel entity, QuestaoRequestDTO request) {
        // Se questão está anulada, não pode ser alterada
        if (entity.isAnulada()) {
            throw new BusinessException("Questão anulada não pode ser alterada");
        }

        // Atualizar prova se necessário
        if (!entity.getProva().getId().equals(request.getProva().getId())) {
            ProvaModel prova = provaRepository.findById(request.getProva().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Prova não encontrada com ID: " + request.getProva().getId()));
            entity.setProva(prova);
        }

        // Atualizar assunto se necessário
        if (!entity.getAssunto().getId().equals(request.getAssunto().getId())) {
            AssuntoModel assunto = assuntoRepository.findById(request.getAssunto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Assunto não encontrado com ID: " + request.getAssunto().getId()));
            entity.setAssunto(assunto);
        }
    }

    @Override
    protected QuestaoResponseDTO toResponse(QuestaoModel entity) {
        QuestaoResponseDTO response = modelMapper.map(entity, QuestaoResponseDTO.class);

        ProvaIdDTO provaDTO = new ProvaIdDTO();
        provaDTO.setId(entity.getProva().getId());
        provaDTO.setNome(entity.getProva().getNome());
        provaDTO.setDataAplicacao(entity.getProva().getDataAplicacao());
        response.setProva(provaDTO);

        AssuntoIdDTO assuntoDTO = new AssuntoIdDTO();
        assuntoDTO.setId(entity.getAssunto().getId());
        assuntoDTO.setNome(entity.getAssunto().getNome());
        response.setAssunto(assuntoDTO);

        return response;
    }

    @Override
    @Transactional
    public void anular(Integer id, String motivo) {
        log.info("Anulando questão ID: {}", id);

        QuestaoModel questao = questaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));

        if (questao.isAnulada()) {
            throw new BusinessException("Questão já está anulada");
        }

        questao.setAnulada(true);
        String comentarioAtual = questao.getComentarioProfessor() != null ?
                questao.getComentarioProfessor() : "";
        questao.setComentarioProfessor(comentarioAtual +
                "\n\n[ANULADA] Motivo: " + motivo + " - Data: " + new java.util.Date());

        questaoRepository.save(questao);

        log.info("Questão ID: {} anulada", id);
    }

    @Override
    @Transactional
    public void reativar(Integer id) {
        log.info("Reativando questão ID: {}", id);

        QuestaoModel questao = questaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));

        if (!questao.isAnulada()) {
            throw new BusinessException("Questão não está anulada");
        }

        questao.setAnulada(false);
        String comentarioAtual = questao.getComentarioProfessor() != null ?
                questao.getComentarioProfessor() : "";
        questao.setComentarioProfessor(comentarioAtual +
                "\n\n[REATIVADA] Data: " + new java.util.Date());

        questaoRepository.save(questao);

        log.info("Questão ID: {} reativada", id);
    }

    @Override
    protected Integer getId(QuestaoModel entity) {
        return entity.getId();
    }
}
