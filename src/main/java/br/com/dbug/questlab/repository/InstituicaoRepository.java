package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.model.InstituicaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface InstituicaoRepository extends JpaRepository<InstituicaoModel,Integer> {

    Optional<InstituicaoModel> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);

    Optional<InstituicaoModel> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<InstituicaoModel> findByRazaoSocial(String razaoSocial);
    boolean existsByRazaoSocial(String razaoSocial);

    Optional<InstituicaoModel> findByTelefone(String telefone);
    boolean existsByTelefone(String telefone);
}