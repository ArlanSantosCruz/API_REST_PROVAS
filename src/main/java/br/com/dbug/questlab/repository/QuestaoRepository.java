package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.model.QuestaoModel;
import br.com.dbug.questlab.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface QuestaoRepository extends JpaRepository<QuestaoModel,Integer> {

    List<QuestaoModel> findByAnulada(boolean anulada);

    Optional<QuestaoModel> findByComentarioProfessor(String comentarioProfessor);
    boolean existsByComentarioProfessor(String comentarioProfessor);

    Optional<QuestaoModel> findByEnunciado(String enunciado);
    boolean existsEnunciado(String enunciado);

}