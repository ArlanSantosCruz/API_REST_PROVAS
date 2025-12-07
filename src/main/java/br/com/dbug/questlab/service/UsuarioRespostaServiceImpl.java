package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.UsuarioRespostaRequestDTO;
import br.com.dbug.questlab.dto.response.UsuarioRespostaResponseDTO;
import br.com.dbug.questlab.dto.simplified.AlternativaIdDTO;
import br.com.dbug.questlab.dto.simplified.QuestaoIdDTO;
import br.com.dbug.questlab.dto.simplified.UsuarioIdDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AlternativaModel;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.model.UsuarioModel;
import br.com.dbug.questlab.model.UsuarioRespostaModel;
import br.com.dbug.questlab.repository.AlternativaRepository;
import br.com.dbug.questlab.repository.QuestaoRepository;
import br.com.dbug.questlab.repository.UsuarioRepository;
import br.com.dbug.questlab.repository.UsuarioRespostaRepository;
import br.com.dbug.questlab.service.UsuarioRespostaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UsuarioRespostaServiceImpl extends CrudServiceImpl<UsuarioRespostaModel, Integer, UsuarioRespostaRequestDTO, UsuarioRespostaResponseDTO>
        implements UsuarioRespostaService {

    private final UsuarioRespostaRepository usuarioRespostaRepository;
    private final UsuarioRepository usuarioRepository;
    private final QuestaoRepository questaoRepository;
    private final AlternativaRepository alternativaRepository;

    public UsuarioRespostaServiceImpl(UsuarioRespostaRepository usuarioRespostaRepository,
                                      UsuarioRepository usuarioRepository,
                                      QuestaoRepository questaoRepository,
                                      AlternativaRepository alternativaRepository,
                                      org.modelmapper.ModelMapper modelMapper) {
        super(usuarioRespostaRepository, modelMapper, UsuarioRespostaModel.class, UsuarioRespostaResponseDTO.class, "Resposta de Usuário");
        this.usuarioRespostaRepository = usuarioRespostaRepository;
        this.usuarioRepository = usuarioRepository;
        this.questaoRepository = questaoRepository;
        this.alternativaRepository = alternativaRepository;
    }

    @Override
    protected void beforeCreate(UsuarioRespostaModel entity, UsuarioRespostaRequestDTO request) {
        // Buscar usuário
        UsuarioModel usuario = usuarioRepository.findById(request.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com ID: " + request.getUsuario().getId()));

        // Verificar se usuário está ativo
        if (!usuario.isAtivo()) {
            throw new BusinessException("Usuário inativo não pode registrar respostas");
        }
        entity.setUsuario(usuario);

        // Buscar questão
        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Questão não encontrada com ID: " + request.getQuestao().getId()));

        // Verificar se questão está anulada
        if (questao.isAnulada()) {
            throw new BusinessException("Não é possível responder questão anulada");
        }
        entity.setQuestao(questao);

        // Buscar alternativa escolhida
        AlternativaModel alternativaEscolhida = alternativaRepository.findById(
                        request.getAlternativaEscolhida().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alternativa não encontrada com ID: " + request.getAlternativaEscolhida().getId()));

        // Verificar se alternativa pertence à questão
        if (!alternativaEscolhida.getQuestao().getId().equals(questao.getId())) {
            throw new BusinessException("Alternativa não pertence à questão informada");
        }
        entity.setAlternativaEscolhida(alternativaEscolhida);

        // Se dataResposta não foi informada, usar data atual
        if (entity.getDataResposta() == null) {
            entity.setDataResposta(new Date());
        }
    }

    @Override
    protected void beforeUpdate(UsuarioRespostaModel entity, UsuarioRespostaRequestDTO request) {
        // Buscar nova alternativa escolhida
        AlternativaModel novaAlternativa = alternativaRepository.findById(
                        request.getAlternativaEscolhida().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alternativa não encontrada com ID: " + request.getAlternativaEscolhida().getId()));

        // Verificar se alternativa pertence à questão
        if (!novaAlternativa.getQuestao().getId().equals(entity.getQuestao().getId())) {
            throw new BusinessException("Alternativa não pertence à questão da resposta");
        }

        entity.setAlternativaEscolhida(novaAlternativa);
    }

    @Override
    protected UsuarioRespostaResponseDTO toResponse(UsuarioRespostaModel entity) {
        UsuarioRespostaResponseDTO response = modelMapper.map(entity, UsuarioRespostaResponseDTO.class);

        UsuarioIdDTO usuarioDTO = new UsuarioIdDTO();
        usuarioDTO.setId(entity.getUsuario().getId());
        usuarioDTO.setNome(entity.getUsuario().getNome());
        usuarioDTO.setEmail(entity.getUsuario().getEmail());
        usuarioDTO.setPerfil(entity.getUsuario().getPerfil());
        response.setUsuario(usuarioDTO);

        QuestaoIdDTO questaoDTO = new QuestaoIdDTO();
        questaoDTO.setId(entity.getQuestao().getId());
        questaoDTO.setEnunciadoResumido(
                entity.getQuestao().getEnunciado().length() > 50 ?
                        entity.getQuestao().getEnunciado().substring(0, 50) + "..." :
                        entity.getQuestao().getEnunciado());
        questaoDTO.setAnulada(entity.getQuestao().isAnulada());
        response.setQuestao(questaoDTO);

        AlternativaIdDTO alternativaDTO = new AlternativaIdDTO();
        alternativaDTO.setId(entity.getAlternativaEscolhida().getId());
        alternativaDTO.setTextoResumido(
                entity.getAlternativaEscolhida().getTexto().length() > 30 ?
                        entity.getAlternativaEscolhida().getTexto().substring(0, 30) + "..." :
                        entity.getAlternativaEscolhida().getTexto());
        alternativaDTO.setCorreta(entity.getAlternativaEscolhida().isCorreta());
        response.setAlternativaEscolhida(alternativaDTO);

        // Calcular se acertou
        response.setAcertou(entity.getAlternativaEscolhida().isCorreta());

        return response;
    }

    @Override
    protected Integer getId(UsuarioRespostaModel entity) {
        return entity.getId();
    }
}
