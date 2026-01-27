package br.com.dbug.questlab.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import br.com.dbug.questlab.rest.dto.filter.CargoFilterDTO;
import br.com.dbug.questlab.rest.dto.response.CargoResponseDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.exception.ResourceNotFoundException;
import br.com.dbug.questlab.model.CargoModel;
import br.com.dbug.questlab.repository.CargoRepository;
import br.com.dbug.questlab.rest.dto.request.CargoRequestDTO;
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
public class CargoService {

    private final CargoRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public CargoResponseDTO create(CargoRequestDTO request) {
        log.info("Criando cargo: {}", request.getNome());

        if (repository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe um cargo com o nome: " + request.getNome());
        }

        CargoModel entity = modelMapper.map(request, CargoModel.class);
        CargoModel saved = repository.save(entity);
        return modelMapper.map(saved, CargoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public CargoResponseDTO findById(Integer id) {
        log.debug("Buscando cargo ID: {}", id);

        CargoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com ID: " + id));

        return modelMapper.map(entity, CargoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<CargoResponseDTO> findAll() {
        log.debug("Listando todos os cargos");

        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CargoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public CargoResponseDTO update(Integer id, CargoRequestDTO request) {
        log.info("Atualizando cargo ID: {}", id);

        CargoModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com ID: " + id));

        if (!entity.getNome().equals(request.getNome()) && repository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe outro cargo com o nome: " + request.getNome());
        }

        entity.setNome(request.getNome());
        entity.setNivelEscolaridade(request.getNivelEscolaridade());

        CargoModel updated = repository.save(entity);
        return modelMapper.map(updated, CargoResponseDTO.class);
    }

    @Transactional
    public void delete(Integer id) {
        log.info("Excluindo cargo ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cargo não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<CargoResponseDTO> findAllPaginated(CargoFilterDTO filter) {
        log.info("Listando cargos com filtros e paginação");

        // Configura a ordenação
        Sort sort = Sort.by(
                filter.getSortDirection() != null && filter.getSortDirection().equalsIgnoreCase("DESC")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                filter.getSortBy() != null ? filter.getSortBy() : "id"
        );

        // Cria o objeto Pageable
        Pageable pageable = PageRequest.of(
                filter.getPage() != null ? filter.getPage() : 0,
                filter.getSize() != null ? filter.getSize() : 10,
                sort
        );

        // Busca com ou sem filtros
        Page<CargoModel> page;

        if (filter.getNome() != null && !filter.getNome().isEmpty()) {
            // Busca por nome (case insensitive)
            page = repository.findByNomeContainingIgnoreCase(filter.getNome(), pageable);
        } else {
            // Busca todos
            page = repository.findAll(pageable);
        }

        // Converte para DTO
        return page.map(cargo -> modelMapper.map(cargo, CargoResponseDTO.class));
    }
}

