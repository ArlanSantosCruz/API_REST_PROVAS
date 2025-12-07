package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.AlternativaModel;
import br.com.dbug.questlab.model.AssuntoModel;
import br.com.dbug.questlab.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface AssuntoRepository extends JpaRepository<UsuarioModel,Integer> {

    Optional<UsuarioModel> findByNome(String nome);

    boolean existsByNome(String nome);

}
