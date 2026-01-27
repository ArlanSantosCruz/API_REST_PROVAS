package br.com.dbug.questlab.repository;

import br.com.dbug.questlab.model.AlternativaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlternativaRepository extends JpaRepository<AlternativaModel,Integer> {

    Optional<AlternativaModel> findByEnunciado(String enunciado);
    boolean existsByEnunciado(String enunciado);

    List<AlternativaModel> findByCorretaTrue();

    }