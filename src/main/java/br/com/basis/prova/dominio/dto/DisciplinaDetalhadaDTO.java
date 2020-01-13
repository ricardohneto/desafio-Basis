package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaDetalhadaDTO {

    private String nome;
    private Integer cargaHoraria;
    private String nomeProfessor;
    @JsonIgnore
    private String idProfessor;

}
