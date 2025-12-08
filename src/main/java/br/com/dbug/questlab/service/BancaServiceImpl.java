package br.com.dbug.questlab.service;

import br.com.dbug.questlab.rest.dto.request.BancaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.BancaResponseDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.repository.BancaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BancaServiceImpl extends CrudServiceImpl<BancaModel, Integer, BancaRequestDTO, BancaResponseDTO>
        implements BancaService {

    private final BancaRepository bancaRepository;

    public BancaServiceImpl(BancaRepository bancaRepository, ModelMapper modelMapper) {
        super(bancaRepository, modelMapper, BancaModel.class, BancaResponseDTO.class, "Banca");
        this.bancaRepository = bancaRepository;
    }

    @Override
    protected void beforeCreate(BancaModel entity, BancaRequestDTO request) {
        // Verificar CNPJ único
        bancaRepository.findByCnpj(request.getCnpj())
                .ifPresent(b -> {
                    throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
                });

    }

    @Override
    protected void beforeUpdate(BancaModel entity, BancaRequestDTO request) {
        // Verificar CNPJ único (se alterado)
        if (!entity.getCnpj().equals(request.getCnpj())) {
            bancaRepository.findByCnpj(request.getCnpj())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("CNPJ já cadastrado: " + request.getCnpj());
                        }
                    });
        }


    }

    @Override
    @Transactional
    public void inactivate(Integer id) {
        log.info("Inativando banca ID: {}", id);

        BancaModel banca = bancaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com ID: " + id));

        banca.setAtivo(false);
        bancaRepository.save(banca);

        log.info("Banca ID: {} inativada", id);
    }

    @Override
    @Transactional
    public void activate(Integer id) {
        log.info("Ativando banca ID: {}", id);

        BancaModel banca = bancaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com ID: " + id));

        banca.setAtivo(true);
        bancaRepository.save(banca);

        log.info("Banca ID: {} ativada", id);
    }

    @Override
    protected Integer getId(BancaModel entity) {
        return entity.getId();
    }
}
