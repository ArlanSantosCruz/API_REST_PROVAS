package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "enunciado", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "O enunciado não pode ser nulo")
    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    @Column(name = "comentarioProfessor", columnDefinition = "TEXT")
    private String comentarioProfessor;

    @Column(name = "anulada", nullable = false)
    private Boolean anulada = false;

    // CORRIGIDO: Usar ProvaModel ao invés de int
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProva", nullable = false)
    @NotNull(message = "A prova não pode ser nula")
    private ProvaModel prova;

    // CORRIGIDO: Usar AssuntoModel ao invés de int
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAssunto", nullable = false)
    @NotNull(message = "O assunto não pode ser nulo")
    private AssuntoModel assunto;
}