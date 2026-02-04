package br.com.dbug.questlab.repository.projection;

import java.time.LocalDate;
//localizar os intens

public interface UsuariosAtivosProjection {
    String getNome();
    String getEmail();
    String getPerfil();
    LocalDate getDataNascimento();
    // SEM getSenha() - Projection já exclui a senha da busca
}