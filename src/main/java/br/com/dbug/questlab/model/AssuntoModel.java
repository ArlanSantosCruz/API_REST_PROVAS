package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assunto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssuntoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    // CORRIGIDO: Usar DisciplinaModel ao invés de int
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDisciplina", nullable = false)
    @NotNull(message = "A disciplina não pode ser nula")
    private DisciplinaModel disciplina;
}
