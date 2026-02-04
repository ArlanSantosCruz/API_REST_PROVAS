package br.com.dbug.questlab.repository.projection;
//localizar os intens

public interface DesempenhoUsuarioProjection {
    Long getTotalQuestoesRespondidas();
    Long getTotalAcertos();
    Long getTotalErros();
    // SEM taxa de aproveitamento - será calculada no Service!
}
