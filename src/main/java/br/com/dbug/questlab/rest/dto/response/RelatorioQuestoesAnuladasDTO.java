package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioQuestoesAnuladasDTO {
    private String enunciadoResumido;
    private String disciplina;
    private String assunto;
    private LocalDate dataAnulacao;
    private String comentarioProfessor;
}

