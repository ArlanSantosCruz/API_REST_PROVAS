package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.CargoModel;
import br.com.dbug.questlab.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel,Integer> {
    Optional<CargoModel> findByNome(String nome);
    boolean existsByNome(String nome);

    Page<CargoModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<CargoModel> findByNivelEscolaridade(String nivelEscolaridade, Pageable pageable);


}