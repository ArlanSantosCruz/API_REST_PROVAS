package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.model.InstituicaoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    boolean existsBySigla(@NotBlank(message = "Sigla é obrigatória") @Size(min = 2, max = 5, message = "Sigla deve ter entre 2 e 5 caracteres") String sigla);
}