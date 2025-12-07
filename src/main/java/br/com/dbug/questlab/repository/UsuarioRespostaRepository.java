package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.UsuarioRespostaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRespostaRepository extends JpaRepository<UsuarioRespostaModel, Integer> {

    Optional<UsuarioRespostaModel> findByUsuarioIdAndQuestaoId(Integer usuarioId, Integer questaoId);

    List<UsuarioRespostaModel> findByUsuarioId(Integer usuarioId);
    List<UsuarioRespostaModel> findByQuestaoId(Integer questaoId);

    List<UsuarioRespostaModel> findByDataRespostaBetween(Date inicio, Date fim);
    List<UsuarioRespostaModel> findByUsuarioIdAndDataRespostaBetween(Integer usuarioId, Date inicio, Date fim);

    boolean existsByUsuarioIdAndQuestaoId(Integer usuarioId, Integer questaoId);
}