package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.ProvaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.dbug.questlab.repository.projection.ProvasConcursoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
    SELECT 
        c.nome as nomeConcurso,
        p.ano as ano,
        p.nome as nomeProva,
        cg.nome as cargoAssociado,
        p.data_aplicacao as dataAplicacao
    FROM prova p
    INNER JOIN concurso c ON p.id_concurso = c.id
    LEFT JOIN cargo cg ON p.id_cargo = cg.id
    WHERE (:concursoId IS NULL OR c.id = :concursoId)
    AND (:ano IS NULL OR p.ano = :ano)
    ORDER BY p.data_aplicacao DESC
""", nativeQuery = true)
    Page<ProvasConcursoProjection> findProvasPorConcurso(
            @Param("concursoId") Integer concursoId,
            @Param("ano") Integer ano,
            Pageable pageable
    );
}