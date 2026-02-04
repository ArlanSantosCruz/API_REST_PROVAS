package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.UsuarioRespostaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.dbug.questlab.repository.projection.DesempenhoUsuarioProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import br.com.dbug.questlab.repository.projection.ContagemRespostasProjection;


@Repository
public interface UsuarioRespostaRepository extends JpaRepository<UsuarioRespostaModel, Integer> {

    Optional<UsuarioRespostaModel> findByUsuarioIdAndQuestaoId(Integer usuarioId, Integer questaoId);

    List<UsuarioRespostaModel> findByUsuarioId(Integer usuarioId);
    List<UsuarioRespostaModel> findByQuestaoId(Integer questaoId);

    List<UsuarioRespostaModel> findByDataRespostaBetween(Date inicio, Date fim);
    List<UsuarioRespostaModel> findByUsuarioIdAndDataRespostaBetween(Integer usuarioId, Date inicio, Date fim);

    boolean existsByUsuarioIdAndQuestaoId(Integer usuarioId, Integer questaoId);

    long countByUsuarioId(Integer usuarioId);

    Long countByUsuarioIdAndAcerto(Integer usuarioId, Boolean acerto);


    long countByQuestaoId(Integer questaoId);

    // Query sem filtro de data
    @Query("""
    SELECT 
        COUNT(ur.id) as totalQuestoesRespondidas,
        SUM(CASE WHEN ur.acerto = true THEN 1 ELSE 0 END) as totalAcertos,
        SUM(CASE WHEN ur.acerto = false THEN 1 ELSE 0 END) as totalErros
    FROM UsuarioRespostaModel ur
    WHERE ur.usuario.id = :usuarioId
""")
    DesempenhoUsuarioProjection findDesempenhoByUsuarioId(@Param("usuarioId") Integer usuarioId);

    // Query com filtro de data
    @Query("""
    SELECT 
        COUNT(ur.id) as totalQuestoesRespondidas,
        SUM(CASE WHEN ur.acerto = true THEN 1 ELSE 0 END) as totalAcertos,
        SUM(CASE WHEN ur.acerto = false THEN 1 ELSE 0 END) as totalErros
    FROM UsuarioRespostaModel ur
    WHERE ur.usuario.id = :usuarioId
    AND ur.dataResposta BETWEEN :dataInicial AND :dataFinal
""")
    DesempenhoUsuarioProjection findDesempenhoByUsuarioIdAndPeriodo(
            @Param("usuarioId") Integer usuarioId,
            @Param("dataInicial") Date dataInicial,
            @Param("dataFinal") Date dataFinal
    );
// Query para contar respostas no período
    @Query("""
    SELECT 
        COUNT(ur.id) as totalQuestoesRespondidas
    FROM UsuarioRespostaModel ur
    WHERE MONTH(ur.dataResposta) = :mes
    AND YEAR(ur.dataResposta) = :ano
""")
    ContagemRespostasProjection contarRespostasPorPeriodo(
            @Param("mes") Integer mes,
            @Param("ano") Integer ano
    );

    // Query para contar respostas do mês anterior (para calcular crescimento)
    @Query("""
    SELECT 
        COUNT(ur.id) as totalQuestoesRespondidas
    FROM UsuarioRespostaModel ur
    WHERE MONTH(ur.dataResposta) = :mes
    AND YEAR(ur.dataResposta) = :ano
""")
    ContagemRespostasProjection contarRespostasMesAnterior(
            @Param("mes") Integer mes,
            @Param("ano") Integer ano
    );
}