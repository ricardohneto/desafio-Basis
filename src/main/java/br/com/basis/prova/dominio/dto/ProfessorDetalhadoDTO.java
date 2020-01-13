package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Disciplina;
import com.fasterxml.jackson.annotation.JsonIgnore;
import liquibase.pro.packaged.J;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorDetalhadoDTO {

    private String nome;
    private String matricula;
    private String nomeDisciplinas;
    @JsonIgnore
    private List<Disciplina> disciplinas = new ArrayList<>();

}
