package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.CargoIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.ConcursoIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvaRequestDTO {

    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A data de aplicação não pode ser nula")
    private Date dataAplicacao;

    @NotNull(message = "O concurso não pode ser nulo")
    @Valid
    private ConcursoIdDTO concurso; // ⬅️ Usar SimplifiedDTO

    @NotNull(message = "O cargo não pode ser nulo")
    @Valid
    private CargoIdDTO cargo; // ⬅️ Usar SimplifiedDTO
}