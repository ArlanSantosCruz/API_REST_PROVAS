package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AssuntoModel;
import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.repository.AssuntoRepository;
import br.com.dbug.questlab.repository.ProvaRepository;
import br.com.dbug.questlab.repository.QuestaoRepository;
import br.com.dbug.questlab.rest.dto.request.QuestaoRequestDTO;
import br.com.dbug.questlab.rest.dto.response.QuestaoResponseDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioDisciplinaDTO;
import br.com.dbug.questlab.rest.dto.simplified.AssuntoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.DisciplinaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ProvaIdDTO;
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
public class QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final ProvaRepository provaRepository;
    private final AssuntoRepository assuntoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public QuestaoResponseDTO create(QuestaoRequestDTO request) {
        log.debug("Criando questão: {}", request);

        // Validar prova
        ProvaModel prova = provaRepository.findById(request.getProva().getId())
                .orElseThrow(() -> ResourceNotFoundException.prova(request.getProva().getId()));

        // Validar assunto
        AssuntoModel assunto = assuntoRepository.findById(request.getAssunto().getId())
                .orElseThrow(() -> ResourceNotFoundException.assunto(request.getAssunto().getId()));

        // Criar entidade
        QuestaoModel entity = modelMapper.map(request, QuestaoModel.class);
        entity.setProva(prova);
        entity.setAssunto(assunto);
        entity.setAnulada(false);

        // Salvar
        QuestaoModel saved = questaoRepository.save(entity);

        log.info("Questão criada com sucesso. ID: {}", saved.getId());

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public QuestaoResponseDTO findById(Integer id) {
        log.debug("Buscando questão por ID: {}", id);

        QuestaoModel entity = questaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.questao(id));

        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<QuestaoResponseDTO> findAll() {
        log.debug("Buscando todas as questões");

        return questaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<QuestaoResponseDTO> findAll(Pageable pageable) {
        log.debug("Buscando questões com paginação: {}", pageable);

        return questaoRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public QuestaoResponseDTO update(Integer id, QuestaoRequestDTO request) {
        log.debug("Atualizando questão ID {}: {}", id, request);

        QuestaoModel entity = questaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.questao(id));

        // Verificar se questão está anulada
        if (entity.getAnulada()) {
            throw new BusinessException("Questão anulada não pode ser alterada");
        }

        // Atualizar prova se necessário
        if (!entity.getProva().getId().equals(request.getProva().getId())) {
            ProvaModel prova = provaRepository.findById(request.getProva().getId())
                    .orElseThrow(() -> ResourceNotFoundException.prova(request.getProva().getId()));
            entity.setProva(prova);
        }

        // Atualizar assunto se necessário
        if (!entity.getAssunto().getId().equals(request.getAssunto().getId())) {
            AssuntoModel assunto = assuntoRepository.findById(request.getAssunto().getId())
                    .orElseThrow(() -> ResourceNotFoundException.assunto(request.getAssunto().getId()));
            entity.setAssunto(assunto);
        }

        // Atualizar campos
        entity.setEnunciado(request.getEnunciado());
        entity.setComentarioProfessor(request.getComentarioProfessor());

        QuestaoModel updated = questaoRepository.save(entity);

        log.info("Questão ID {} atualizada com sucesso", id);

        return toResponse(updated);
    }

    @Transactional
    public void delete(Integer id) {
        log.debug("Removendo questão ID: {}", id);

        if (!questaoRepository.existsById(id)) {
            throw ResourceNotFoundException.questao(id);
        }

        questaoRepository.deleteById(id);

        log.info("Questão ID {} removida com sucesso", id);
    }

    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return questaoRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return questaoRepository.count();
    }

    @Transactional
    public void anular(Integer id, String motivo) {
        log.info("Anulando questão ID: {}", id);

        QuestaoModel questao = questaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.questao(id));

        if (questao.getAnulada()) {
            throw new BusinessException("Questão já está anulada");
        }

        questao.setAnulada(true);
        String comentarioAtual = questao.getComentarioProfessor() != null ?
                questao.getComentarioProfessor() : "";
        questao.setComentarioProfessor(comentarioAtual +
                "\n\n[ANULADA] Motivo: " + motivo + " - Data: " + new Date());

        questaoRepository.save(questao);

        log.info("Questão ID: {} anulada", id);
    }

    @Transactional
    public void reativar(Integer id) {
        log.info("Reativando questão ID: {}", id);

        QuestaoModel questao = questaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.questao(id));

        if (!questao.getAnulada()) {
            throw new BusinessException("Questão não está anulada");
        }

        questao.setAnulada(false);
        String comentarioAtual = questao.getComentarioProfessor() != null ?
                questao.getComentarioProfessor() : "";
        questao.setComentarioProfessor(comentarioAtual +
                "\n\n[REATIVADA] Data: " + new Date());

        questaoRepository.save(questao);

        log.info("Questão ID: {} reativada", id);
    }

    /**
     * UC-01: Gera relatório de questões por disciplina
     *
     * Retorna uma lista com:
     * - Nome da disciplina
     * - Total de questões ativas
     * - Total de questões anuladas
     *
     * @return Lista de relatórios por disciplina
     */
    @Transactional(readOnly = true)
    public List<RelatorioDisciplinaDTO> gerarRelatorioPorDisciplina() {
        log.info("Gerando relatório de questões por disciplina");

        List<RelatorioDisciplinaDTO> relatorio = questaoRepository.gerarRelatorioPorDisciplina();

        log.info("Relatório gerado com {} disciplinas", relatorio.size());

        return relatorio;
    }

    // Método auxiliar para conversão
    private QuestaoResponseDTO toResponse(QuestaoModel entity) {
        QuestaoResponseDTO response = modelMapper.map(entity, QuestaoResponseDTO.class);

        // Mapear prova
        ProvaIdDTO provaDTO = new ProvaIdDTO();
        provaDTO.setId(entity.getProva().getId());
        provaDTO.setNome(entity.getProva().getNome());
        provaDTO.setDataAplicacao(entity.getProva().getDataAplicacao());
        response.setProva(provaDTO);

        // Mapear assunto com disciplina
        AssuntoIdDTO assuntoDTO = new AssuntoIdDTO();
        assuntoDTO.setId(entity.getAssunto().getId());
        assuntoDTO.setNome(entity.getAssunto().getNome());

        DisciplinaIdDTO disciplinaDTO = new DisciplinaIdDTO();
        disciplinaDTO.setId(entity.getAssunto().getDisciplina().getId());
        disciplinaDTO.setNome(entity.getAssunto().getDisciplina().getNome());
        assuntoDTO.setDisciplina(disciplinaDTO);

        response.setAssunto(assuntoDTO);

        return response;
    }
}