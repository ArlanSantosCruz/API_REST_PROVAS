package br.com.dbug.questlab.repository;
import br.com.dbug.questlab.repository.projection.QuestoesAnuladasProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.rest.dto.response.RelatorioDisciplinaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.dbug.questlab.repository.projection.QuestoesPorDisciplinaProjection;
import br.com.dbug.questlab.repository.projection.IndicadoresQuestoesProjection;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestaoRepository extends JpaRepository<QuestaoModel, Integer> {

    // Métodos existentes
    List<QuestaoModel> findByAnulada(boolean anulada);

    Optional<QuestaoModel> findByComentarioProfessor(String comentarioProfessor);

    boolean existsByComentarioProfessor(String comentarioProfessor);

    Optional<QuestaoModel> findByEnunciado(String enunciado);

    boolean existsByEnunciado(String enunciado);

    List<QuestaoModel> findByProvaId(Integer provaId);

    List<QuestaoModel> findByAssuntoId(Integer assuntoId);


    //Retorna um relatório com a quantidade de questões por disciplina,
    //separando questões ativas e anuladas.
    //@return Lista de relatórios por disciplina

    @Query("SELECT new br.com.dbug.questlab.rest.dto.response.RelatorioDisciplinaDTO(" +
            "    d.id, " +
            "    d.nome, " +
            "    SUM(CASE WHEN q.anulada = false THEN 1 ELSE 0 END), " +
            "    SUM(CASE WHEN q.anulada = true THEN 1 ELSE 0 END) " +
            ") " +
            "FROM QuestaoModel q " +
            "JOIN q.assunto a " +
            "JOIN a.disciplina d " +
            "GROUP BY d.id, d.nome " +
            "ORDER BY d.nome")
    List<RelatorioDisciplinaDTO> gerarRelatorioPorDisciplina();

    //Conta total de questões ativas (não anuladas)

    long countByAnulada(boolean anulada);

    //Conta questões por disciplina
    @Query("SELECT COUNT(q) FROM QuestaoModel q " +
            "JOIN q.assunto a " +
            "WHERE a.disciplina.id = :disciplinaId")
    long countByDisciplinaId(Integer disciplinaId);


    //Conta questões ativas por disciplina

    @Query("SELECT COUNT(q) FROM QuestaoModel q " +
            "JOIN q.assunto a " +
            "WHERE a.disciplina.id = :disciplinaId AND q.anulada = false")
    long countAtivasByDisciplinaId(Integer disciplinaId);


    //Conta questões anuladas por disciplina

    @Query("SELECT COUNT(q) FROM QuestaoModel q " +
            "JOIN q.assunto a " +
            "WHERE a.disciplina.id = :disciplinaId AND q.anulada = true")
    long countAnuladasByDisciplinaId(Integer disciplinaId);

    @Query("""
                SELECT 
                    d.id as disciplinaId,
                    d.nome as disciplinaNome,
                    COUNT(CASE WHEN q.anulada = false OR q.anulada IS NULL THEN 1 END) as totalQuestoes,
                    COUNT(CASE WHEN q.anulada = true THEN 1 END) as totalQuestoesAnuladas
                FROM QuestaoModel q
                INNER JOIN q.assunto a
                INNER JOIN a.disciplina d
                GROUP BY d.id, d.nome
                ORDER BY d.nome
            """)
    List<QuestoesPorDisciplinaProjection> findQuestoesPorDisciplina();


    @Query("""
    SELECT 
        q.enunciado as enunciado,
        d.nome as disciplina,
        a.nome as assunto,
        q.dataAnulacao as dataAnulacao,
        q.comentarioProfessor as comentarioProfessor
    FROM QuestaoModel q
    INNER JOIN q.assunto a
    INNER JOIN a.disciplina d
    WHERE q.anulada = true
    AND q.dataAnulacao BETWEEN :dataInicial AND :dataFinal
    ORDER BY q.dataAnulacao DESC
""")
    Page<QuestoesAnuladasProjection> findQuestoesAnuladasPorPeriodo(
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal,
            Pageable pageable
    );

    @Query("""
    SELECT COUNT(q.id)
    FROM QuestaoModel q
    INNER JOIN q.assunto a
    WHERE a.disciplina.id = :disciplinaId
    AND q.anulada = true
""")
    Long countQuestoesAnuladasByDisciplina(@Param("disciplinaId") Integer disciplinaId);

    @Query("""
    SELECT 
        COUNT(q.id) as totalQuestoesCadastradas,
        (SELECT COUNT(DISTINCT ur.questao.id) 
         FROM UsuarioRespostaModel ur) as totalQuestoesResolvidas
    FROM QuestaoModel q
""")
    IndicadoresQuestoesProjection getIndicadoresQuestoes();

    // Query para contar questões do mês anterior (crescimento)
    @Query("""
    SELECT COUNT(q.id)
    FROM QuestaoModel q
    WHERE MONTH(q.dataCadastro) = :mes
    AND YEAR(q.dataCadastro) = :ano
""")
    Long countQuestoesPorMes(@Param("mes") Integer mes, @Param("ano") Integer ano);

}