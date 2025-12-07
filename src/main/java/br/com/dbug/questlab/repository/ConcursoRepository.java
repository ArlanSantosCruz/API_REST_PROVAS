package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.ConcursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface ConcursoRepository extends JpaRepository<ConcursoModel,Integer> {

    Optional<ConcursoModel> findByNome(String nome);
    boolean existsByNome(String nome);
}