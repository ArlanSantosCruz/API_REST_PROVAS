package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "concurso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "ano", nullable = false)
    @NotNull(message = "O ano não pode ser nulo")
    @Temporal(TemporalType.DATE)
    private Date ano;

    @Column(name = "cancelado", nullable = false)
    private Boolean cancelado = false;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBanca", nullable = false)
    @NotNull(message = "A banca não pode ser nula")
    private BancaModel banca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstituicao", nullable = false)
    @NotNull(message = "A instituição não pode ser nula")
    private InstituicaoModel instituicao;
}
