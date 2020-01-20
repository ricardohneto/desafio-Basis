package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoDTO {

    @NotNull
    private Integer idAluno;

    private String nomeAluno;

    @NotNull
    private Integer idDisciplina;

    private String nomeDisciplina;

    @NotNull
    @Min(0)
    @Max(10)
    private Double nota;

    @NotNull
    private LocalDate dataAplicacao;

}
