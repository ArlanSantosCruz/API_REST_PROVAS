package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.ProvaRequestDTO;
import br.com.dbug.questlab.dto.response.ProvaResponseDTO;
import br.com.dbug.questlab.dto.simplified.CargoIdDTO;
import br.com.dbug.questlab.dto.simplified.ConcursoIdDTO;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.CargoModel;
import br.com.dbug.questlab.model.ConcursoModel;
import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.repository.CargoRepository;
import br.com.dbug.questlab.repository.ConcursoRepository;
import br.com.dbug.questlab.repository.ProvaRepository;
import br.com.dbug.questlab.service.ProvaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProvaServiceImpl extends CrudServiceImpl<ProvaModel, Integer, ProvaRequestDTO, ProvaResponseDTO>
        implements ProvaService {

    private final ProvaRepository provaRepository;
    private final ConcursoRepository concursoRepository;
    private final CargoRepository cargoRepository;

    public ProvaServiceImpl(ProvaRepository provaRepository,
                            ConcursoRepository concursoRepository,
                            CargoRepository cargoRepository,
                            org.modelmapper.ModelMapper modelMapper) {
        super(provaRepository, modelMapper, ProvaModel.class, ProvaResponseDTO.class, "Prova");
        this.provaRepository = provaRepository;
        this.concursoRepository = concursoRepository;
        this.cargoRepository = cargoRepository;
    }

    @Override
    protected void beforeCreate(ProvaModel entity, ProvaRequestDTO request) {
        // Buscar concurso
        ConcursoModel concurso = concursoRepository.findById(request.getConcurso().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Concurso não encontrado com ID: " + request.getConcurso().getId()));
        entity.setConcurso(concurso);

        // Buscar cargo
        CargoModel cargo = cargoRepository.findById(request.getCargo().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cargo não encontrado com ID: " + request.getCargo().getId()));
        entity.setCargo(cargo);
    }

    @Override
    protected void beforeUpdate(ProvaModel entity, ProvaRequestDTO request) {
        // Atualizar concurso se necessário
        if (!entity.getConcurso().getId().equals(request.getConcurso().getId())) {
            ConcursoModel concurso = concursoRepository.findById(request.getConcurso().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Concurso não encontrado com ID: " + request.getConcurso().getId()));
            entity.setConcurso(concurso);
        }

        // Atualizar cargo se necessário
        if (!entity.getCargo().getId().equals(request.getCargo().getId())) {
            CargoModel cargo = cargoRepository.findById(request.getCargo().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cargo não encontrado com ID: " + request.getCargo().getId()));
            entity.setCargo(cargo);
        }
    }

    @Override
    protected ProvaResponseDTO toResponse(ProvaModel entity) {
        ProvaResponseDTO response = modelMapper.map(entity, ProvaResponseDTO.class);

        ConcursoIdDTO concursoDTO = new ConcursoIdDTO();
        concursoDTO.setId(entity.getConcurso().getId());
        concursoDTO.setNome(entity.getConcurso().getNome());
        response.setConcurso(concursoDTO);

        CargoIdDTO cargoDTO = new CargoIdDTO();
        cargoDTO.setId(entity.getCargo().getId());
        cargoDTO.setNome(entity.getCargo().getNome());
        response.setCargo(cargoDTO);

        return response;
    }

    @Override
    protected Integer getId(ProvaModel entity) {
        return entity.getId();
    }
}