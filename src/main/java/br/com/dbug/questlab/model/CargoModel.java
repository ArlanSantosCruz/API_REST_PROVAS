package br.com.dbug.questlab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cargo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 150, nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "nivelEscolaridade", length = 150, nullable = false)
    @NotNull(message = "A escolaridade não pode ser nula")
    @NotBlank(message = "A escolaridade é obrigatória")
    private String nivelEscolaridade;
}