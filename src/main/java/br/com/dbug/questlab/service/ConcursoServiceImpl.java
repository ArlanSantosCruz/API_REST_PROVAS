package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.ConcursoRequestDTO;
import br.com.dbug.questlab.dto.response.ConcursoResponseDTO;
import br.com.dbug.questlab.dto.simplified.BancaIdDTO;
import br.com.dbug.questlab.dto.simplified.InstituicaoIdDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.model.ConcursoModel;
import br.com.dbug.questlab.model.InstituicaoModel;
import br.com.dbug.questlab.repository.BancaRepository;
import br.com.dbug.questlab.repository.ConcursoRepository;
import br.com.dbug.questlab.repository.InstituicaoRepository;
import br.com.dbug.questlab.service.ConcursoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ConcursoServiceImpl extends CrudServiceImpl<ConcursoModel, Integer, ConcursoRequestDTO, ConcursoResponseDTO>
        implements ConcursoService {

    private final ConcursoRepository concursoRepository;
    private final BancaRepository bancaRepository;
    private final InstituicaoRepository instituicaoRepository;

    public ConcursoServiceImpl(ConcursoRepository concursoRepository,
                               BancaRepository bancaRepository,
                               InstituicaoRepository instituicaoRepository,
                               org.modelmapper.ModelMapper modelMapper) {
        super(concursoRepository, modelMapper, ConcursoModel.class, ConcursoResponseDTO.class, "Concurso");
        this.concursoRepository = concursoRepository;
        this.bancaRepository = bancaRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    @Override
    protected void beforeCreate(ConcursoModel entity, ConcursoRequestDTO request) {
        if (concursoRepository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe um concurso com o nome: " + request.getNome());
        }

        // Buscar banca
        BancaModel banca = bancaRepository.findById(request.getBanca().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Banca não encontrada com ID: " + request.getBanca().getId()));
        entity.setBanca(banca);

        // Buscar instituição
        InstituicaoModel instituicao = instituicaoRepository.findById(request.getInstituicao().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Instituição não encontrada com ID: " + request.getInstituicao().getId()));
        entity.setInstituicao(instituicao);
    }

    @Override
    protected void beforeUpdate(ConcursoModel entity, ConcursoRequestDTO request) {
        if (!entity.getNome().equals(request.getNome())) {
            concursoRepository.findByNome(request.getNome())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("Já existe outro concurso com o nome: " + request.getNome());
                        }
                    });
        }

        // Atualizar banca se necessário
        if (!entity.getBanca().getId().equals(request.getBanca().getId())) {
            BancaModel banca = bancaRepository.findById(request.getBanca().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Banca não encontrada com ID: " + request.getBanca().getId()));
            entity.setBanca(banca);
        }

        // Atualizar instituição se necessário
        if (!entity.getInstituicao().getId().equals(request.getInstituicao().getId())) {
            InstituicaoModel instituicao = instituicaoRepository.findById(request.getInstituicao().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Instituição não encontrada com ID: " + request.getInstituicao().getId()));
            entity.setInstituicao(instituicao);
        }
    }

    @Override
    protected ConcursoResponseDTO toResponse(ConcursoModel entity) {
        ConcursoResponseDTO response = modelMapper.map(entity, ConcursoResponseDTO.class);

        BancaIdDTO bancaDTO = new BancaIdDTO();
        bancaDTO.setId(entity.getBanca().getId());
        bancaDTO.setRazaoSocial(entity.getBanca().getRazaoSocial());
        bancaDTO.setSigla(entity.getBanca().getSigla());
        response.setBanca(bancaDTO);

        InstituicaoIdDTO instituicaoDTO = new InstituicaoIdDTO();
        instituicaoDTO.setId(entity.getInstituicao().getId());
        instituicaoDTO.setRazaoSocial(entity.getInstituicao().getRazaoSocial());
        instituicaoDTO.setSigla(entity.getInstituicao().getSigla());
        response.setInstituicao(instituicaoDTO);

        return response;
    }

    @Override
    @Transactional
    public void cancelar(Integer id) {
        log.info("Cancelando concurso ID: {}", id);

        ConcursoModel concurso = concursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));

        concurso.setCancelado(true);
        concursoRepository.save(concurso);

        log.info("Concurso ID: {} cancelado", id);
    }

    @Override
    @Transactional
    public void ativar(Integer id) {
        log.info("Ativando concurso ID: {}", id);

        ConcursoModel concurso = concursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));

        concurso.setAtivo(true);
        concursoRepository.save(concurso);

        log.info("Concurso ID: {} ativado", id);
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        log.info("Inativando concurso ID: {}", id);

        ConcursoModel concurso = concursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com ID: " + id));

        concurso.setAtivo(false);
        concursoRepository.save(concurso);

        log.info("Concurso ID: {} inativado", id);
    }

    @Override
    protected Integer getId(ConcursoModel entity) {
        return entity.getId();
    }
}