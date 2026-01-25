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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Transactional
    public AlternativaResponseDTO create(AlternativaRequestDTO request) {
        log.info("Criando alternativa");

        QuestaoModel questao = questaoRepository.findById(request.getQuestao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada"));

        if (questao.getAnulada()) {
            throw new BusinessException("Não é possível adicionar alternativa a uma questão anulada");
        }

        if (request.getCorreta()) {
            List<AlternativaModel> corretas = repository.findByCorretaTrue();
            boolean jaExisteCorreta = corretas.stream()
                    .anyMatch(a -> a.getQuestao().getId().equals(questao.getId()));
            if (jaExisteCorreta) {
                throw new BusinessException("Já existe uma alternativa correta para esta questão");
            }
        }

        AlternativaModel entity = new AlternativaModel();
        entity.setTexto(request.getTexto());
        entity.setEnunciado(request.getEnunciado());
        entity.setCorreta(request.getCorreta());
        entity.setQuestao(questao);
        }

