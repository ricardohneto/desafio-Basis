package br.com.basis.prova.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AVALIACAO")
@Getter
@Setter
@NoArgsConstructor
public class Avaliacao{

    @EmbeddedId
    private AvaliacaoId id;

    @MapsId("idAluno")
    @JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Aluno aluno;

    @MapsId("idDisciplina")
    @JoinColumn(name = "ID_DISCIPLINA", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Disciplina disciplina;

    @Column(name = "NOTA", nullable = false)
    private Double nota;

    @Column(name = "DATA_AVALIACAO", nullable = false)
    private LocalDate dataAplicacao;



}
