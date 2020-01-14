package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorDTO {


    private Integer id;

    @NotNull
    @Size(min = 1, max = 50, message = "Nome Inválido")
    private String nome;

    @NotNull
    @Size(min = 6, max = 6, message = "Matricula Inválida")
    private String matricula;

    @Size(min = 1, max = 200, message = "Área de Atuação Inválida")
    private String area;

    @NotNull
    private LocalDate dataNascimento;

    private Integer idade;

    private List<DisciplinaDTO> disciplinas = new ArrayList<>();

}
