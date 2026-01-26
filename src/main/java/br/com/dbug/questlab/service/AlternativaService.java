package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.AlternativaModel;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.repository.AlternativaRepository;
import br.com.dbug.questlab.repository.QuestaoRepository;
import br.com.dbug.questlab.rest.dto.request.AlternativaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.AlternativaResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.QuestaoIdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor


public class AlternativaService {

    private final AlternativaRepository repository;
    private final QuestaoRepository questaoRepository;

    @Transactional
    public AlternativaResponseDTO create(AlternativaRequestDTO request) {
        log.info("Criando alternativa");

        // Buscar questão
        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada"));

        // Verificar se questão está anulada
        if (questao.getAnulada()) {
            throw new BusinessException("Não é possível adicionar alternativa a uma questão anulada");
        }

        // Verificar se já existe alternativa correta
        if (request.getCorreta()) {
            List<AlternativaModel> corretas = repository.findByCorretaTrue();
            boolean jaExisteCorreta = corretas.stream()
                    .anyMatch(a -> a.getQuestao().getId().equals(questao.getId()));

            if (jaExisteCorreta) {
                throw new BusinessException("Já existe uma alternativa correta para esta questão");
            }
        }

        // Criar entidade
        AlternativaModel entity = new AlternativaModel();
        entity.setTexto(request.getTexto());
        entity.setEnunciado(request.getEnunciado());
        entity.setCorreta(request.getCorreta());
        entity.setQuestao(questao);

        // Salvar
        AlternativaModel saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public AlternativaResponseDTO findById(Integer id) {
        log.debug("Buscando alternativa ID: {}", id);

        AlternativaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alternativa não encontrada com ID: " + id));

        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<AlternativaResponseDTO> findAll() {
        log.debug("Listando todas as alternativas");

        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlternativaResponseDTO update(Integer id, AlternativaRequestDTO request) {
        log.info("Atualizando alternativa ID: {}", id);

        AlternativaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alternativa não encontrada com ID: " + id));

        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada"));

        if (questao.getAnulada()) {
            throw new BusinessException("Não é possível alterar alternativa de questão anulada");
        }

        // Atualizar campos
        entity.setTexto(request.getTexto());
        entity.setEnunciado(request.getEnunciado());
        entity.setCorreta(request.getCorreta());
        entity.setQuestao(questao);

        AlternativaModel updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo alternativa ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alternativa não encontrada com ID: " + id);
        }

        repository.deleteById(id);
    }

    private AlternativaResponseDTO toResponseDTO(AlternativaModel entity) {
        AlternativaResponseDTO dto = new AlternativaResponseDTO();
        dto.setId(entity.getId());
        dto.setTexto(entity.getTexto());
        dto.setEnunciado(entity.getEnunciado());
        dto.setCorreta(entity.getCorreta());

        QuestaoIdDTO questaoDTO = new QuestaoIdDTO();
        questaoDTO.setId(entity.getQuestao().getId());
        String enunciado = entity.getQuestao().getEnunciado();
        questaoDTO.setEnunciadoResumido(enunciado.length() > 50 ?
                enunciado.substring(0, 50) + "..." : enunciado);
        questaoDTO.setAnulada(entity.getQuestao().getAnulada());
        dto.setQuestao(questaoDTO);

        return dto;


    }
}