package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.DisciplinaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<DisciplinaModel,Integer> {

    Optional<DisciplinaModel> findByNome(String nome);
    boolean existsByNome(String nome);

}
