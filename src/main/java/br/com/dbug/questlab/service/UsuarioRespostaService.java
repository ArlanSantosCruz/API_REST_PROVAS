package br.com.dbug.questlab.service;

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
import br.com.dbug.questlab.rest.dto.request.UsuarioRespostaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.UsuarioRespostaResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.AlternativaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.UsuarioIdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioRespostaService {

    private final UsuarioRespostaRepository usuarioRespostaRepository;
    private final UsuarioRepository usuarioRepository;
    private final QuestaoRepository questaoRepository;
    private final AlternativaRepository alternativaRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UsuarioRespostaResponseDTO create(UsuarioRespostaRequestDTO request) {
        log.debug("Criando resposta de usuário: {}", request);

        // Validar usuário
        UsuarioModel usuario = usuarioRepository.findById(request.getUsuario().getId())
                .orElseThrow(() -> ResourceNotFoundException.usuario(request.getUsuario().getId()));

        if (!usuario.getAtivo()) {
            throw BusinessException.usuarioInativo();
        }

        // Validar questão
        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> ResourceNotFoundException.questao(request.getQuestao().getId()));

        if (questao.getAnulada()) {
            throw new BusinessException("Não é possível responder questão anulada");
        }

        // Verificar se usuário já respondeu esta questão
        if (usuarioRespostaRepository.existsByUsuarioIdAndQuestaoId(
                request.getUsuario().getId(), request.getQuestao().getId())) {
            throw new BusinessException("Usuário já respondeu esta questão");
        }

        // Validar alternativa
        AlternativaModel alternativa = alternativaRepository.findById(
                        request.getAlternativaEscolhida().getId())
                .orElseThrow(() -> ResourceNotFoundException.alternativa(
                        request.getAlternativaEscolhida().getId()));

        // Verificar se alternativa pertence à questão
        if (!alternativa.getQuestao().getId().equals(questao.getId())) {
            throw new BusinessException("Alternativa não pertence à questão informada");
        }

        // Criar entidade
        UsuarioRespostaModel entity = new UsuarioRespostaModel();
        entity.setUsuario(usuario);
        entity.setQuestao(questao);
        entity.setAlternativaEscolhida(alternativa);
        entity.setDataResposta(request.getDataResposta() != null ?
                request.getDataResposta() : new Date());

        // Salvar
        UsuarioRespostaModel saved = usuarioRespostaRepository.save(entity);

        log.info("Resposta de usuário criada com sucesso. ID: {}", saved.getId());

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UsuarioRespostaResponseDTO findById(Integer id) {
        log.debug("Buscando resposta de usuário por ID: {}", id);

        UsuarioRespostaModel entity = usuarioRespostaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta de Usuário", id));

        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findAll() {
        log.debug("Buscando todas as respostas de usuário");

        return usuarioRespostaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UsuarioRespostaResponseDTO> findAll(Pageable pageable) {
        log.debug("Buscando respostas de usuário com paginação: {}", pageable);

        return usuarioRespostaRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public UsuarioRespostaResponseDTO update(Integer id, UsuarioRespostaRequestDTO request) {
        log.debug("Atualizando resposta de usuário ID {}: {}", id, request);

        UsuarioRespostaModel entity = usuarioRespostaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta de Usuário", id));

        // Validar nova alternativa
        AlternativaModel novaAlternativa = alternativaRepository.findById(
                        request.getAlternativaEscolhida().getId())
                .orElseThrow(() -> ResourceNotFoundException.alternativa(
                        request.getAlternativaEscolhida().getId()));

        // Verificar se alternativa pertence à questão
        if (!novaAlternativa.getQuestao().getId().equals(entity.getQuestao().getId())) {
            throw new BusinessException("Alternativa não pertence à questão da resposta");
        }

        // Atualizar
        entity.setAlternativaEscolhida(novaAlternativa);
        if (request.getDataResposta() != null) {
            entity.setDataResposta(request.getDataResposta());
        }

        UsuarioRespostaModel updated = usuarioRespostaRepository.save(entity);

        log.info("Resposta de usuário ID {} atualizada com sucesso", id);

        return toResponse(updated);
    }

    @Transactional
    public void delete(Integer id) {
        log.debug("Removendo resposta de usuário ID: {}", id);

        if (!usuarioRespostaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resposta de Usuário", id);
        }

        usuarioRespostaRepository.deleteById(id);

        log.info("Resposta de usuário ID {} removida com sucesso", id);
    }

    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return usuarioRespostaRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return usuarioRespostaRepository.count();
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findByUsuarioId(Integer usuarioId) {
        log.debug("Buscando respostas do usuário ID: {}", usuarioId);

        return usuarioRespostaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findByQuestaoId(Integer questaoId) {
        log.debug("Buscando respostas da questão ID: {}", questaoId);

        return usuarioRespostaRepository.findByQuestaoId(questaoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findByUsuarioIdAndQuestaoId(Integer usuarioId, Integer questaoId) {
        log.debug("Buscando respostas do usuário {} na questão {}", usuarioId, questaoId);

        return usuarioRespostaRepository.findByUsuarioIdAndQuestaoId(usuarioId, questaoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean usuarioJaRespondeu(Integer usuarioId, Integer questaoId) {
        return usuarioRespostaRepository.existsByUsuarioIdAndQuestaoId(usuarioId, questaoId);
    }

    @Transactional(readOnly = true)
    public Double calcularTaxaAcerto(Integer usuarioId) {
        log.debug("Calculando taxa de acerto do usuário ID: {}", usuarioId);

        long total = usuarioRespostaRepository.countByUsuarioId(usuarioId);
        if (total == 0) {
            return 0.0;
        }

        long acertos = countRespostasCorretasByUsuarioId(usuarioId);

        return (acertos * 100.0) / total;
    }

    @Transactional(readOnly = true)
    public Double calcularTaxaAcertoPorDisciplina(Integer usuarioId, Integer disciplinaId) {
        log.debug("Calculando taxa de acerto do usuário {} na disciplina {}", usuarioId, disciplinaId);

        List<UsuarioRespostaModel> respostas = usuarioRespostaRepository.findByUsuarioId(usuarioId);

        List<UsuarioRespostaModel> respostasDisciplina = respostas.stream()
                .filter(r -> r.getQuestao().getAssunto().getDisciplina().getId().equals(disciplinaId))
                .collect(Collectors.toList());

        if (respostasDisciplina.isEmpty()) {
            return 0.0;
        }

        long acertos = respostasDisciplina.stream()
                .filter(r -> r.getAlternativaEscolhida().getCorreta())
                .count();

        return (acertos * 100.0) / respostasDisciplina.size();
    }

    @Transactional(readOnly = true)
    public Double calcularTaxaAcertoPorAssunto(Integer usuarioId, Integer assuntoId) {
        log.debug("Calculando taxa de acerto do usuário {} no assunto {}", usuarioId, assuntoId);

        List<UsuarioRespostaModel> respostas = usuarioRespostaRepository.findByUsuarioId(usuarioId);

        List<UsuarioRespostaModel> respostasAssunto = respostas.stream()
                .filter(r -> r.getQuestao().getAssunto().getId().equals(assuntoId))
                .collect(Collectors.toList());

        if (respostasAssunto.isEmpty()) {
            return 0.0;
        }

        long acertos = respostasAssunto.stream()
                .filter(r -> r.getAlternativaEscolhida().getCorreta())
                .count();

        return (acertos * 100.0) / respostasAssunto.size();
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findRespostasIncorretasByUsuarioId(Integer usuarioId) {
        log.debug("Buscando respostas incorretas do usuário ID: {}", usuarioId);

        return usuarioRespostaRepository.findByUsuarioIdAndAcerto(usuarioId, false).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findRespostasCorretasByUsuarioId(Integer usuarioId) {
        log.debug("Buscando respostas corretas do usuário ID: {}", usuarioId);

        return usuarioRespostaRepository.findByUsuarioIdAndAcerto(usuarioId, true).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaResponseDTO> findByUsuarioIdAndPeriodo(Integer usuarioId,
                                                                      Date dataInicio,
                                                                      Date dataFim) {
        log.debug("Buscando respostas do usuário {} entre {} e {}", usuarioId, dataInicio, dataFim);

        return usuarioRespostaRepository.findByUsuarioIdAndDataRespostaBetween(
                        usuarioId, dataInicio, dataFim).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countByUsuarioId(Integer usuarioId) {
        return usuarioRespostaRepository.countByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public long countByQuestaoId(Integer questaoId) {
        return usuarioRespostaRepository.countByQuestaoId(questaoId);
    }

    @Transactional(readOnly = true)
    public long countRespostasCorretasByUsuarioId(Integer usuarioId) {
        return usuarioRespostaRepository.findByUsuarioIdAndAcerto(usuarioId, true).size();
    }

    @Transactional(readOnly = true)
    public long countRespostasIncorretasByUsuarioId(Integer usuarioId) {
        return usuarioRespostaRepository.findByUsuarioIdAndAcerto(usuarioId, false).size();
    }

    // Método auxiliar para conversão
    private UsuarioRespostaResponseDTO toResponse(UsuarioRespostaModel entity) {
        UsuarioRespostaResponseDTO response = new UsuarioRespostaResponseDTO();
        response.setId(entity.getId());
        response.setDataResposta(entity.getDataResposta());

        // Mapear usuário
        UsuarioIdDTO usuarioDTO = new UsuarioIdDTO();
        usuarioDTO.setId(entity.getUsuario().getId());
        usuarioDTO.setNome(entity.getUsuario().getNome());
        usuarioDTO.setEmail(entity.getUsuario().getEmail());
        usuarioDTO.setPerfil(entity.getUsuario().getPerfil());
        response.setUsuario(usuarioDTO);

        // Mapear questão
        QuestaoIdDTO questaoDTO = new QuestaoIdDTO();
        questaoDTO.setId(entity.getQuestao().getId());
        String enunciado = entity.getQuestao().getEnunciado();
        questaoDTO.setEnunciadoResumido(
                enunciado.length() > 50 ? enunciado.substring(0, 50) + "..." : enunciado);
        questaoDTO.setAnulada(entity.getQuestao().getAnulada());
        response.setQuestao(questaoDTO);

        // Mapear alternativa escolhida
        AlternativaIdDTO alternativaDTO = new AlternativaIdDTO();
        alternativaDTO.setId(entity.getAlternativaEscolhida().getId());
        String texto = entity.getAlternativaEscolhida().getTexto();
        alternativaDTO.setTextoResumido(
                texto.length() > 30 ? texto.substring(0, 30) + "..." : texto);
        alternativaDTO.setCorreta(entity.getAlternativaEscolhida().getCorreta());
        response.setAlternativaEscolhida(alternativaDTO);

        // Calcular se acertou
        response.setAcertou(entity.getAlternativaEscolhida().getCorreta());

        return response;
    }
}