package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.AssuntoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssuntoRepository extends JpaRepository<AssuntoModel, Integer> {

    Optional<AssuntoModel> findByNome(String nome);
    boolean existsByNome(String nome);

    // CORRIGIDO: Método para buscar por disciplina
    List<AssuntoModel> findByDisciplinaId(Integer disciplinaId);

    // CORRIGIDO: Método para verificar duplicidade por disciplina
    Optional<AssuntoModel> findByNomeAndDisciplinaId(String nome, Integer disciplinaId);
    boolean existsByNomeAndDisciplinaId(String nome, Integer disciplinaId);
}