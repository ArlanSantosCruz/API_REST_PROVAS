package br.com.dbug.questlab.repository;
import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface BancaRepository extends JpaRepository<BancaModel,Integer> {

    Optional<BancaModel> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);

    Optional<BancaModel> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<BancaModel> findByTelefone(String telefone);
    boolean existsByTelefone(String telefone);
}
