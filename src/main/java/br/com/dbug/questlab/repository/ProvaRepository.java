package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.ProvaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProvaRepository extends JpaRepository<ProvaModel, Integer> {

    Optional<ProvaModel> findByNome(String nome);

    boolean existsByNome(String nome);

    Optional<ProvaModel> findByDataAplicacao(Date dataAplicacao);

    boolean existsByDataAplicacao(Date dataAplicacao);

    List<ProvaModel> findByDataAplicacaoBetween(Date inicio, Date fim);

    List<ProvaModel> findByConcursoId(Integer concursoId);

    List<ProvaModel> findByCargoId(Integer cargoId);

}