package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "usuarioResposta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRespostaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dataResposta", nullable = false)
    @NotNull(message = "A data de resposta não pode ser nula")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataResposta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    @NotNull(message = "O usuário não pode ser nulo")
    private UsuarioModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuestao", nullable = false)
    @NotNull(message = "A questão não pode ser nula")
    private QuestaoModel questao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAlternativaEscolhida", nullable = false)
    @NotNull(message = "A alternativa escolhida não pode ser nula")
    private AlternativaModel alternativaEscolhida;
}