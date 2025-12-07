package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "prova")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "dataAplicacao", nullable = false)
    @NotNull(message = "A data de aplicação não pode ser nula")
    @Temporal(TemporalType.DATE)
    private Date dataAplicacao;

    // CORRIGIDO: Usar ConcursoModel ao invés de int
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConcurso", nullable = false)
    @NotNull(message = "O concurso não pode ser nulo")
    private ConcursoModel concurso;

    // CORRIGIDO: Usar CargoModel ao invés de int
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCargo", nullable = false)
    @NotNull(message = "O cargo não pode ser nulo")
    private CargoModel cargo;
}
