package br.com.dbug.questlab.rest.dto.response;


import br.com.dbug.questlab.rest.dto.simplified.CargoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ConcursoIdDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProvaResponseDTO {
    private Integer id;
    private String nome;
    private Date dataAplicacao;
    private ConcursoIdDTO concurso;
    private CargoIdDTO cargo;
}
