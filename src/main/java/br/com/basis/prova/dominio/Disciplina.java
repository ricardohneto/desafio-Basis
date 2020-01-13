package br.com.basis.prova.dominio;

import br.com.basis.prova.dominio.dto.AlunoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DISCIPLINA")
@Getter
@Setter
@NoArgsConstructor
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "CARGA_HORARIA", nullable = false)
    private Integer cargaHoraria;

    @Column(name = "ATIVA", nullable = false)
    private Integer ativa;

    @JoinColumn(name = "ID_PROFESSOR", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Professor professor;

//    @JsonBackReference
//    @ManyToMany(mappedBy = "disciplinas", fetch = FetchType.LAZY)
//    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ALUNO_DISCIPLINA",
            joinColumns = @JoinColumn(name = "ID_DISCIPLINA", referencedColumnName = "ID"),
            inverseJoinColumns= @JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID"))
    private List<Aluno> alunos;
}
