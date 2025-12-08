package br.com.dbug.questlab.rest.dto.request;

import br.com.dbug.questlab.rest.dto.simplified.BancaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.InstituicaoIdDTO;
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
public class ConcursoRequestDTO {

    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O ano não pode ser nulo")
    private Date ano;

    private Boolean cancelado = false;
    private Boolean ativo = true;

    // IMPORTANTE: Usar SimplifiedDTO, não Integer!
    @NotNull(message = "A banca não pode ser nula")
    @Valid
    private BancaIdDTO banca;

    @NotNull(message = "A instituição não pode ser nula")
    @Valid
    private InstituicaoIdDTO instituicao;
}