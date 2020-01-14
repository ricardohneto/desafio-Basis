package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Disciplina;
import com.fasterxml.jackson.annotation.JsonIgnore;
import liquibase.pro.packaged.J;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorDetalhadoDTO {


    private Integer id;
    private String nome;
    private String matricula;
    private String area;
    private LocalDate dataNascimento;
    private Integer idade;
    private List<DisciplinaDTO> disciplinas = new ArrayList<>();
    private List<String> nomeDisciplinas = new ArrayList<>();

}
