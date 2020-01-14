package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.*;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.ProfessorDetalhadoMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfessorServico {

    private ProfessorRepositorio professorRepositorio;
    private ProfessorMapper professorMapper;
    private ProfessorDetalhadoMapper professorDetalhadoMapper;
    private DisciplinaRepositorio disciplinaRepositorio;

    public ProfessorServico(ProfessorMapper professorMapper,
                            ProfessorRepositorio professorRepositorio,
                            ProfessorDetalhadoMapper professorDetalhadoMapper,
                            DisciplinaRepositorio disciplinaRepositorio) {
        this.professorMapper = professorMapper;
        this.professorDetalhadoMapper = professorDetalhadoMapper;
        this.professorRepositorio = professorRepositorio;
        this.disciplinaRepositorio = disciplinaRepositorio;
    }

    public ProfessorDTO salvar(ProfessorDTO professorDto) {
        Professor professor = this.professorMapper.toEntity(professorDto);

        if (verificarMatricula(professor))
            throw new RegraNegocioException("Matricula Já Existe");

        return this.professorMapper.toDto(this.professorRepositorio.save(professor));
    }

    private boolean verificarMatricula(Professor professor){
        Professor professorMatricula = this.professorRepositorio.findByMatricula(professor.getMatricula());
        return !(professorMatricula == null || professorMatricula.getId().equals(professor.getId()));
    }

    public void excluir(Integer id) {
        Professor professor = this.professorRepositorio.findById(id).orElseThrow(() ->
                new RegraNegocioException("Identificador do Professor Não Encontrado"));

        if (!(this.disciplinaRepositorio.findByProfessor(professor) == null))
            throw new RegraNegocioException("Professor Ministrando Disciplina(s)");

        this.professorRepositorio.delete(professor);
    }


    public List<ProfessorDTO> consultar() {
        List<ProfessorDTO> professores = this.professorMapper.toDto(this.professorRepositorio.findAll());
        preencherIdades(professores);
        return professores;
    }

    private void preencherIdades(List<ProfessorDTO> professores) {
        professores.forEach(professorDTO -> {
            professorDTO.setIdade(LocalDate.now().getYear() - professorDTO.getDataNascimento().getYear());
        });
    }

    public List<ProfessorDetalhadoDTO> detalhar() {
        List<ProfessorDetalhadoDTO> professores = this.professorDetalhadoMapper.toDto(this.professorRepositorio.findAll());
        preencherIdadesAndNomeDisciplinas(professores);
        return professores;
    }

    private void preencherIdadesAndNomeDisciplinas(List<ProfessorDetalhadoDTO> professores) {
        professores.forEach(professorDetalhado -> {
            List<String> nomeDisciplinas = new ArrayList<>();
            professorDetalhado.setIdade(LocalDate.now().getYear() - professorDetalhado.getDataNascimento().getYear());
            List<DisciplinaDTO> disciplinas = professorDetalhado.getDisciplinas();
            for (DisciplinaDTO disciplina : disciplinas) {
                if (disciplina.getAtiva() == 1)
                    nomeDisciplinas.add(disciplina.getNome());
            }
            professorDetalhado.setNomeDisciplinas(nomeDisciplinas);
        });
    }

    // metodo para classe DisciplinaServico

    protected Professor addProfessor(DisciplinaDTO disciplinaDto) {
        Professor professor = this.professorRepositorio.findByMatricula(disciplinaDto.getIdProfessor());

        if (professor == null)
            throw new RegraNegocioException("Matricula do Professor Não Encontrada");

        return professor;
    }

    protected String findNomeProfessor(String matricula){
        Professor professor = this.professorRepositorio.findByMatricula(matricula);
        return professor.getNome();
    }

}
