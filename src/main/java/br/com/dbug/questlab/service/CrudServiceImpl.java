package br.com.dbug.questlab.service;

import br.com.dbug.questlab.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class CrudServiceImpl<Entity, ID, RequestDTO, ResponseDTO>
        implements CrudService<ID, RequestDTO, ResponseDTO> {

    protected final JpaRepository<Entity, ID> repository;
    protected final ModelMapper modelMapper;
    protected final Class<Entity> entityClass;
    protected final Class<ResponseDTO> responseDTOClass;
    protected final String entityName;

    public CrudServiceImpl(JpaRepository<Entity, ID> repository,
                           ModelMapper modelMapper,
                           Class<Entity> entityClass,
                           Class<ResponseDTO> responseDTOClass,
                           String entityName) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.responseDTOClass = responseDTOClass;
        this.entityName = entityName;
    }

    @Override
    @Transactional
    public ResponseDTO create(RequestDTO request) {
        log.info("Criando {}", entityName);

        Entity entity = modelMapper.map(request, entityClass);
        beforeCreate(entity, request);

        Entity savedEntity = repository.save(entity);
        afterCreate(savedEntity);

        log.info("{} criado(a) com ID: {}", entityName, getId(savedEntity));
        return toResponse(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDTO findById(ID id) {
        log.debug("Buscando {} por ID: {}", entityName, id);

        Entity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName, id));

        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseDTO> findAll() {
        log.debug("Listando todos(as) {}", entityName);

        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseDTO> findAllPaginated(Object filter) {
        log.debug("Listando {}s com paginação", entityName);

        Pageable pageable = createPageable(filter);
        Page<Entity> page = repository.findAll(pageable);

        return page.map(this::toResponse);
    }

    @Override
    @Transactional
    public ResponseDTO update(ID id, RequestDTO request) {
        log.info("Atualizando {} ID: {}", entityName, id);

        Entity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName, id));

        modelMapper.map(request, entity);
        beforeUpdate(entity, request);

        Entity updatedEntity = repository.save(entity);
        afterUpdate(updatedEntity);

        log.info("{} ID: {} atualizado(a)", entityName, id);
        return toResponse(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        log.info("Excluindo {} ID: {}", entityName, id);

        Entity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName, id));

        beforeDelete(entity);
        repository.delete(entity);
        afterDelete(id);

        log.info("{} ID: {} excluído(a)", entityName, id);
    }

    // Métodos template (hook methods) para serem sobrescritos pelas subclasses
    protected void beforeCreate(Entity entity, RequestDTO request) {}
    protected void afterCreate(Entity entity) {}
    protected void beforeUpdate(Entity entity, RequestDTO request) {}
    protected void afterUpdate(Entity entity) {}
    protected void beforeDelete(Entity entity) {}
    protected void afterDelete(ID id) {}

    // Método para conversão customizada (pode ser sobrescrito)
    protected ResponseDTO toResponse(Entity entity) {
        return modelMapper.map(entity, responseDTOClass);
    }

    // Método abstrato para obter o ID da entidade
    protected abstract ID getId(Entity entity);

    // Método para criar Pageable a partir do filtro
    private Pageable createPageable(Object filter) {
        try {
            Integer page = (Integer) filter.getClass().getMethod("getPage").invoke(filter);
            Integer size = (Integer) filter.getClass().getMethod("getSize").invoke(filter);
            String sortBy = (String) filter.getClass().getMethod("getSortBy").invoke(filter);
            String sortDirection = (String) filter.getClass().getMethod("getSortDirection").invoke(filter);

            Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            return PageRequest.of(page, size, Sort.by(direction, sortBy));
        } catch (Exception e) {
            log.warn("Erro ao criar Pageable, usando valores padrão", e);
            return PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        }
    }
}