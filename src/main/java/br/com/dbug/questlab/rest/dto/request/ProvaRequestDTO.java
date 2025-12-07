package br.com.dbug.questlab.rest.dto.request;


import br.com.dbug.questlab.rest.dto.simplified.CargoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ConcursoIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProvaRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
    private String nome;

    @NotNull(message = "Data de aplicação é obrigatória")
    private Date dataAplicacao;

    @NotNull(message = "Concurso é obrigatório")
    private ConcursoIdDTO concurso;

    @NotNull(message = "Cargo é obrigatório")
    private CargoIdDTO cargo;
}
