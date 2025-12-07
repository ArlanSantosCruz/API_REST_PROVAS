package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//extends pode usar JpaRepository ou CRUDrepository
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel,Integer> {
    Optional<UsuarioModel> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<UsuarioModel> findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    Optional<UsuarioModel> findByTelefone(String telefone);
    boolean existsByTelefone(String telefone);

    Optional<UsuarioModel> findByNome(String nome);
    boolean existsByNome(String nome);

    Optional<UsuarioModel> findByPerfil(String perfil);
    boolean existsByPerfil(String perfil);

    Optional<UsuarioModel> findByCelular(String celular);
    boolean existsByCelular(String celular);
}
