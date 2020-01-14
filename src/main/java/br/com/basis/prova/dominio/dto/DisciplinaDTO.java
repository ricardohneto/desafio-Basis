package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Professor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import liquibase.pro.packaged.J;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisciplinaDTO {

    private Integer id;

    @NotNull
    @Size(min = 1, max = 50, message = "Nome Inválido")
    private String nome;

    @NotNull
    @Size(min = 1, max = 200, message = "Descrição Inválida")
    private String descricao;

    @NotNull
    private Integer cargaHoraria;

    private Integer ativa;

    @NotNull
    private String idProfessor;

}
