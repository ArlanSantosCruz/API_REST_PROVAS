package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.ProvaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface ProvaRepository extends JpaRepository<ProvaModel,Integer> {

    Optional<ProvaModel> FindByDataAplicacao(String dataAplicacao);
    boolean existsByDataAplicacao(Date dataAplicacao);

    List<ProvaModel> findByDataResposta(Date inicio, Date fim);

    List<ProvaModel> findByIdUsuarioAndDataResposta(int idUsuario, Date inicio, Date fim);

}
