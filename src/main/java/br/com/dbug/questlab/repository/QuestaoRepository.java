package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.rest.dto.response.RelatorioDisciplinaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}