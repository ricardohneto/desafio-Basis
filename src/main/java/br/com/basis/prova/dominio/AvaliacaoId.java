package br.com.basis.prova.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AvaliacaoId implements Serializable {

    @Column(name = "ID_DISCIPLINA")
    private Integer idDisciplina;

    @Column(name = "ID_ALUNO")
    private Integer idAluno;

}
