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
import br.com.dbug.questlab.rest.dto.simplified.AssuntoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.DisciplinaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ProvaIdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestaoService {

    private final QuestaoRepository repository;
    private final ProvaRepository provaRepository;
    private final AssuntoRepository assuntoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public QuestaoResponseDTO create(QuestaoRequestDTO request) {
        log.info("Criando questão");

        ProvaModel prova = provaRepository.findById(request.getProva().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Prova não encontrada"));
        AssuntoModel assunto = assuntoRepository.findById(request.getAssunto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado"));

        QuestaoModel entity = new QuestaoModel();
        entity.setEnunciado(request.getEnunciado());
        entity.setComentarioProfessor(request.getComentarioProfessor());
        entity.setAnulada(false);
        entity.setProva(prova);
        entity.setAssunto(assunto);

        QuestaoModel saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public QuestaoResponseDTO findById(Integer id) {
        log.debug("Buscando questão ID: {}", id);

        QuestaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));

        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<QuestaoResponseDTO> findAll() {
        log.debug("Listando todas as questões");

        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestaoResponseDTO update(Integer id, QuestaoRequestDTO request) {
        log.info("Atualizando questão ID: {}", id);

        QuestaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));

        if (entity.getAnulada()) {
            throw new BusinessException("Questão anulada não pode ser alterada");
        }

        ProvaModel prova = provaRepository.findById(request.getProva().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Prova não encontrada"));
        AssuntoModel assunto = assuntoRepository.findById(request.getAssunto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado"));

        entity.setEnunciado(request.getEnunciado());
        entity.setComentarioProfessor(request.getComentarioProfessor());
        entity.setProva(prova);
        entity.setAssunto(assunto);

        QuestaoModel updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo questão ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Questão não encontrada com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void anular(Integer id, String motivo) {
        QuestaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));

        if (entity.getAnulada()) {
            throw new BusinessException("Questão já está anulada");
        }

        entity.setAnulada(true);
        String comentario = entity.getComentarioProfessor() != null ? entity.getComentarioProfessor() : "";
        entity.setComentarioProfessor(comentario + "\n[ANULADA] Motivo: " + motivo + " - " + new Date());
        repository.save(entity);
    }

    @Transactional
    public void reativar(Integer id) {
        QuestaoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));

        if (!entity.getAnulada()) {
            throw new BusinessException("Questão não está anulada");
        }

        entity.setAnulada(false);
        repository.save(entity);
    }

    private QuestaoResponseDTO toResponseDTO(QuestaoModel entity) {
        QuestaoResponseDTO dto = new QuestaoResponseDTO();
        dto.setId(entity.getId());
        dto.setEnunciado(entity.getEnunciado());
        dto.setComentarioProfessor(entity.getComentarioProfessor());
        dto.setAnulada(entity.getAnulada());

        ProvaIdDTO provaDTO = new ProvaIdDTO();
        provaDTO.setId(entity.getProva().getId());
        provaDTO.setNome(entity.getProva().getNome());
        provaDTO.setDataAplicacao(entity.getProva().getDataAplicacao());
        dto.setProva(provaDTO);

        AssuntoIdDTO assuntoDTO = new AssuntoIdDTO();
        assuntoDTO.setId(entity.getAssunto().getId());
        assuntoDTO.setNome(entity.getAssunto().getNome());

        DisciplinaIdDTO disciplinaDTO = new DisciplinaIdDTO();
        disciplinaDTO.setId(entity.getAssunto().getDisciplina().getId());
        disciplinaDTO.setNome(entity.getAssunto().getDisciplina().getNome());
        assuntoDTO.setDisciplina(disciplinaDTO);

        dto.setAssunto(assuntoDTO);

        return dto;
    }
}