package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import liquibase.pro.packaged.J;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaViewDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;

}