package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alternativa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "texto", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "O texto não pode ser nulo")
    @NotBlank(message = "O texto é obrigatório")
    private String texto;

    @Column(name = "enunciado", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "O enunciado não pode ser nulo")
    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    @Column(name = "correta", nullable = false)
    @NotNull(message = "O campo correta não pode ser nulo")
    private Boolean correta = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuestao", nullable = false)
    @NotNull(message = "A questão não pode ser nula")
    private QuestaoModel questao;
}