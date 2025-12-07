package br.com.dbug.questlab.rest.dto.request;


import br.com.dbug.questlab.rest.dto.simplified.BancaIdDTO;
import br.com.dbug.questlab.rest.dto.simplified.InstituicaoIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConcursoRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
    private String nome;

    @NotNull(message = "Ano é obrigatório")
    private Date ano;

    private Boolean cancelado = false;
    private Boolean ativo = true;

    @NotNull(message = "Banca é obrigatória")
    private BancaIdDTO banca;

    @NotNull(message = "Instituição é obrigatória")
    private InstituicaoIdDTO instituicao;
}
