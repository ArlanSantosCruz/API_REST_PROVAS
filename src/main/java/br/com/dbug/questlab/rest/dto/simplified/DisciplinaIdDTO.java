package br.com.dbug.questlab.rest.dto.simplified;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaIdDTO {
    private Integer id;
    private String nome;
}