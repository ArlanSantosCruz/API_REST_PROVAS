package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.CargoModel;
import br.com.dbug.questlab.model.ConcursoModel;
import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.repository.CargoRepository;
import br.com.dbug.questlab.repository.ConcursoRepository;
import br.com.dbug.questlab.repository.ProvaRepository;
import br.com.dbug.questlab.rest.dto.request.ProvaRequestDTO;
import br.com.dbug.questlab.rest.dto.response.ProvaResponseDTO;
import br.com.dbug.questlab.rest.dto.simplified.CargoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ConcursoIdDTO;
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
public class ProvaService {

    private final ProvaRepository repository;
    private final ConcursoRepository concursoRepository;
    private final CargoRepository cargoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ProvaResponseDTO create(ProvaRequestDTO request) {
        log.info("Criando prova: {}", request.getNome());

        ConcursoModel concurso = concursoRepository.findById(request.getConcurso().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado"));
        CargoModel cargo = cargoRepository.findById(request.getCargo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado"));

        ProvaModel entity = new ProvaModel();
        entity.setNome(request.getNome());
        entity.setDataAplicacao(request.getDataAplicacao());
        entity.setConcurso(concurso);
        entity.setCargo(cargo);

        ProvaModel saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public ProvaResponseDTO findById(Integer id) {
        log.debug("Buscando prova ID: {}", id);

        ProvaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prova não encontrada com ID: " + id));

        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ProvaResponseDTO> findAll() {
        log.debug("Listando todas as provas");

        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProvaResponseDTO update(Integer id, ProvaRequestDTO request) {
        log.info("Atualizando prova ID: {}", id);

        ProvaModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prova não encontrada com ID: " + id));

        ConcursoModel concurso = concursoRepository.findById(request.getConcurso().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado"));
        CargoModel cargo = cargoRepository.findById(request.getCargo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado"));

        entity.setNome(request.getNome());
        entity.setDataAplicacao(request.getDataAplicacao());
        entity.setConcurso(concurso);
        entity.setCargo(cargo);

        ProvaModel updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo prova ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Prova não encontrada com ID: " + id);
        }

        repository.deleteById(id);
    }

    private ProvaResponseDTO toResponseDTO(ProvaModel entity) {
        ProvaResponseDTO dto = new ProvaResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDataAplicacao(entity.getDataAplicacao());

        ConcursoIdDTO concursoDTO = new ConcursoIdDTO();
        concursoDTO.setId(entity.getConcurso().getId());
        concursoDTO.setNome(entity.getConcurso().getNome());
        dto.setConcurso(concursoDTO);

        CargoIdDTO cargoDTO = new CargoIdDTO();
        cargoDTO.setId(entity.getCargo().getId());
        cargoDTO.setNome(entity.getCargo().getNome());
        dto.setCargo(cargoDTO);

        return dto;
    }
}