package br.com.dbug.questlab.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface CrudService<ID, RequestDTO, ResponseDTO, C> {
    ResponseDTO create(RequestDTO request);
    ResponseDTO findById(ID id);
    List<ResponseDTO> findAll();
    Page<ResponseDTO> findAllPaginated(Object filter);
    ResponseDTO update(ID id, RequestDTO request);
    void delete(ID id);
}