package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AlunoDetalhadoDTO {

    private String nome;
    private String matricula;
    private Integer idade;
    @JsonIgnore
    private LocalDate dataNascimento;
    private String nomeDisciplinas;
    @JsonIgnore
    private List<DisciplinaDTO> disciplinas;

}
