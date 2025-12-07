package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.ProvaModel;
import br.com.dbug.questlab.model.UsuarioRespostaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface UsuarioRespostaRepository extends JpaRepository<UsuarioRespostaRepository,Integer> {

    Optional<UsuarioRespostaModel> findBydataResposta(Long usuarioId, Long questaoId);

}