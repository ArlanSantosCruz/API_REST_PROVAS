package br.com.dbug.questlab.service;

import br.com.dbug.questlab.dto.request.CargoRequestDTO;
import br.com.dbug.questlab.dto.response.CargoResponseDTO;
import br.com.dbug.questlab.exception.BusinessException;
import br.com.dbug.questlab.model.CargoModel;
import br.com.dbug.questlab.repository.CargoRepository;
import br.com.dbug.questlab.service.CargoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CargoServiceImpl extends CrudServiceImpl<CargoModel, Integer, CargoRequestDTO, CargoResponseDTO>
        implements CargoService {

    private final CargoRepository cargoRepository;

    public CargoServiceImpl(CargoRepository cargoRepository, org.modelmapper.ModelMapper modelMapper) {
        super(cargoRepository, modelMapper, CargoModel.class, CargoResponseDTO.class, "Cargo");
        this.cargoRepository = cargoRepository;
    }

    @Override
    protected void beforeCreate(CargoModel entity, CargoRequestDTO request) {
        if (cargoRepository.existsByNome(request.getNome())) {
            throw new BusinessException("Já existe um cargo com o nome: " + request.getNome());
        }
    }

    @Override
    protected void beforeUpdate(CargoModel entity, CargoRequestDTO request) {
        if (!entity.getNome().equals(request.getNome())) {
            cargoRepository.findByNome(request.getNome())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(entity.getId())) {
                            throw new BusinessException("Já existe outro cargo com o nome: " + request.getNome());
                        }
                    });
        }
    }

    @Override
    protected Integer getId(CargoModel entity) {
        return entity.getId();
    }
}