package br.com.dbug.questlab.rest.dto.simplified;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaIdDTO {
    private Integer id;
    private String textoResumido;
    private Boolean correta;
}