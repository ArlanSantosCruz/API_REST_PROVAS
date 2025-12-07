package br.com.dbug.questlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

public class UsuarioRespostaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "dataResposta", nullable = false)
    private Date dataResposta;

    @Column(name = "idUsuario", nullable = false)
    private int idUsuario;

    @Column(name = "idQuestao", nullable = false)
    private int idQuestao;

    @Column(name = "idAlternativaEscolhida", nullable = false)
    private int idAlternativaEscolhida;
}
