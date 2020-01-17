package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoDTO {

    @NotNull
    private Integer idAluno;

    private Aluno aluno;

    @NotNull
    private Integer idDisciplina;

    private Disciplina disciplina;

    @NotNull
    @Size(min = 0, max = 10, message = "Nota Inválida")
    private Double nota;

    @NotNull
    private LocalDate dataAplicacao;

}
