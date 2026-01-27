package br.com.dbug.questlab.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioProvasConcursoDTO {
    private String nomeConcurso;
    private Integer ano;
    private String nomeProva;
    private String cargoAssociado;
    private LocalDate dataAplicacao;
}
