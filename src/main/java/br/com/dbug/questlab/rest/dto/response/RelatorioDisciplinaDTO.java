package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDisciplinaDTO {

    //ID da disciplina
    private Integer disciplinaId;

    //Nome da disciplina
    private String disciplinaNome;

    //Total de questões ativas (não anuladas)
    private Long totalQuestoes;

    //Total de questões anuladas
    private Long totalQuestoesAnuladas;

    //Total geral de questões (ativas + anuladas)
    private Long totalGeral;


    public RelatorioDisciplinaDTO(Integer disciplinaId, String disciplinaNome,
                                  Long totalQuestoes, Long totalQuestoesAnuladas) {
        this.disciplinaId = disciplinaId;
        this.disciplinaNome = disciplinaNome;
        this.totalQuestoes = totalQuestoes;
        this.totalQuestoesAnuladas = totalQuestoesAnuladas;
        this.totalGeral = totalQuestoes + totalQuestoesAnuladas;
    }



}