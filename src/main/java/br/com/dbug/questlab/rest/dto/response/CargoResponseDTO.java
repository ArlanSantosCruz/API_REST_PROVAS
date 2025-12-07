package br.com.dbug.questlab.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoResponseDTO {
    private Integer id;
    private String nome;
    private String escolaridade;
}