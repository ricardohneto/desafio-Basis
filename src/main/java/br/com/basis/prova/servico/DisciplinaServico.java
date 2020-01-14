package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.*;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaDetalhadoMapper;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DisciplinaServico {

    private DisciplinaRepositorio disciplinaRepositorio;
    private DisciplinaMapper disciplinaMapper;
    private DisciplinaDetalhadoMapper disciplinaDetalhadoMapper;

    private ProfessorServico professorServico;
    private AlunoRepositorio alunoRepositorio;

    public DisciplinaServico(DisciplinaMapper disciplinaMapper,
                             DisciplinaRepositorio disciplinaRepositorio,
                             DisciplinaDetalhadoMapper disciplinaDetalhadoMapper,
                             ProfessorServico professorServico,
                             AlunoRepositorio alunoRepositorio) {
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaRepositorio = disciplinaRepositorio;
        this.disciplinaDetalhadoMapper = disciplinaDetalhadoMapper;
        this.professorServico = professorServico;
        this.alunoRepositorio = alunoRepositorio;
    }

    public DisciplinaDTO salvar(DisciplinaDTO disciplinaDto) {
        Disciplina disciplina = this.disciplinaMapper.toEntity(disciplinaDto);

        disciplina.setProfessor(this.professorServico.addProfessor(disciplinaDto));

        return this.disciplinaMapper.toDto(this.disciplinaRepositorio.save(disciplina));
    }

    public Disciplina matricular(DisciplinaDTO disciplinaDto, String matricula) {
        Disciplina disciplina = this.disciplinaRepositorio.findById(disciplinaDto.getId()).get();
        disciplina.setProfessor(this.professorServico.addProfessor(disciplinaDto));

        Aluno aluno = this.alunoRepositorio.findByMatricula(matricula);

        // tratar caso a matricula seja repetida

        if (aluno == null)
            throw new RegraNegocioException("Matricula Não Encontrada");


        if(disciplina.getAlunos() == null) {
            disciplina.setAlunos(new ArrayList<>());
        }

        disciplina.getAlunos().add(aluno);

        return this.disciplinaRepositorio.save(disciplina);
    }

    public void excluir(Integer id) {
        Disciplina disciplina = this.disciplinaRepositorio.findById(id).orElseThrow(() ->
                new RegraNegocioException("Identificador da Disciplina Não Encontrado"));

        if(!(this.alunoRepositorio.findByDisciplinas(disciplina).isEmpty()))
            throw new RegraNegocioException("Disciplina Com Alunos Matriculados");

        // como fazer de forma melhor?
        disciplina.setProfessor(null);
        //

        this.disciplinaRepositorio.delete(disciplina);
    }

    public List<DisciplinaDTO> consultar() {
        return this.disciplinaMapper.toDto(this.disciplinaRepositorio.findAll());
    }

    public List<DisciplinaDetalhadaDTO> detalhar() {
        List<DisciplinaDetalhadaDTO> disciplinas = this.disciplinaDetalhadoMapper.toDto(this.disciplinaRepositorio.findAll());
        preencherNomeProfessores(disciplinas);
        return disciplinas;
    }

    private void preencherNomeProfessores(List<DisciplinaDetalhadaDTO> disciplinas){
        for (DisciplinaDetalhadaDTO disciplina : disciplinas) {
            disciplina.setNomeProfessor(this.professorServico.findNomeProfessor(disciplina.getIdProfessor()));
        }
    }

}
