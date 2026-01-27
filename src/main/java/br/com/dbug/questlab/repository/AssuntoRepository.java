package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.AssuntoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssuntoRepository extends JpaRepository<AssuntoModel, Integer> {

    Optional<AssuntoModel> findByNome(String nome);
    boolean existsByNome(String nome);

    //Método para buscar por disciplina
    List<AssuntoModel> findByDisciplinaId(Integer disciplinaId);

    // Método para verificar duplicidade por disciplina
    Optional<AssuntoModel> findByNomeAndDisciplinaId(String nome, Integer disciplinaId);
    boolean existsByNomeAndDisciplinaId(String nome, Integer disciplinaId);


    // Busca paginada por disciplina
    Page<AssuntoModel> findByDisciplinaId(Integer disciplinaId, Pageable pageable);

    // Busca paginada por nome (case insensitive)
    Page<AssuntoModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);





}