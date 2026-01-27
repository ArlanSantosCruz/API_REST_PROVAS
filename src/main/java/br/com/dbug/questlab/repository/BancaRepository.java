package br.com.dbug.questlab.repository;
import br.com.dbug.questlab.model.BancaModel;
import br.com.dbug.questlab.model.UsuarioModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BancaRepository extends JpaRepository<BancaModel,Integer> {

    Optional<BancaModel> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);

    Optional<BancaModel> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<BancaModel> findByTelefone(String telefone);
    boolean existsByTelefone(String telefone);

    boolean existsBySigla(@NotBlank(message = "Sigla é obrigatória") @Size(min = 2, max = 5, message = "Sigla deve ter entre 2 e 5 caracteres") String sigla);

    Page<BancaModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
