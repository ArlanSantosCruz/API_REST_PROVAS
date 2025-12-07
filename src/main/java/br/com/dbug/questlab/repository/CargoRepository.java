package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.CargoModel;
import br.com.dbug.questlab.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface CargoRepository extends JpaRepository<CargoModel,Integer> {
    Optional<CargoModel> findByNome(String nome);
    boolean existsByNome(String nome);

}