package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Professor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import liquibase.pro.packaged.J;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisciplinaDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private Integer ativa;
    private String idProfessor;
    @JsonIgnore
    private List<AlunoDTO> alunos = new ArrayList<>();

}
