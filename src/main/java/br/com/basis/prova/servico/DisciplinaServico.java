package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.*;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.mapper.DisciplinaDetalhadoMapper;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import br.com.basis.prova.servico.mapper.DisciplinaViewMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
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
    private DisciplinaViewMapper disciplinaViewMapper;

    private AlunoServico alunoServico;
    private ProfessorServico professorServico;

    public DisciplinaServico(DisciplinaMapper disciplinaMapper,
                             DisciplinaRepositorio disciplinaRepositorio,
                             DisciplinaDetalhadoMapper disciplinaDetalhadoMapper,
                             DisciplinaViewMapper disciplinaViewMapper,
                             AlunoServico alunoServico,
                             ProfessorServico professorServico) {
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaRepositorio = disciplinaRepositorio;
        this.disciplinaDetalhadoMapper = disciplinaDetalhadoMapper;
        this.disciplinaViewMapper = disciplinaViewMapper;
        this.alunoServico = alunoServico;
        this.professorServico = professorServico;
    }

    public Disciplina salvar(DisciplinaDTO disciplinaDto) {

        // testar aqui
        Disciplina disciplina = this.disciplinaMapper.toEntity(disciplinaDto);

        disciplina.setProfessor(this.professorServico.addProfessor(disciplinaDto));

        return this.disciplinaRepositorio.save(disciplina);
    }

    public Disciplina matricular(Integer id) {
        DisciplinaDTO disciplinaDto = this.disciplinaMapper.toDto(this.disciplinaRepositorio.findById(id).get());

        Disciplina disciplina = this.disciplinaMapper.toEntity(disciplinaDto);
        disciplina.setProfessor(this.professorServico.addProfessor(disciplinaDto));
        //disciplina.getAlunos().add(this.alunoServico.addAluno());

        return this.disciplinaRepositorio.save(disciplina);
    }

    public boolean excluir(Integer id) {
        if(this.disciplinaRepositorio.findById(id).get().getAlunos().isEmpty()
                || this.disciplinaRepositorio.findById(id).get().getAlunos() == null) {
            Disciplina disciplina = this.disciplinaRepositorio.getOne(id);
            disciplina.setProfessor(null);
            disciplina.setAlunos(null);
            this.disciplinaRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    public DisciplinaDTO consultaUm(Integer id){
        return this.disciplinaMapper.toDto(this.disciplinaRepositorio.getOne(id));
    }

    public List<DisciplinaViewDTO> consultar() {
        return this.disciplinaViewMapper.toDto(this.disciplinaRepositorio.findAll());
    }

    public List<DisciplinaDetalhadaDTO> detalhar() {
        return setDisciplinaDetalhadatoDto();
    }

    public List<DisciplinaDetalhadaDTO> setDisciplinaDetalhadatoDto(){
        List<DisciplinaDetalhadaDTO> disciplinas = this.disciplinaDetalhadoMapper.toDto(this.disciplinaRepositorio.findAll());

        for (DisciplinaDetalhadaDTO disciplina: disciplinas) {
            disciplina.setNomeProfessor(this.professorServico.findNomeProfessor(disciplina.getIdProfessor()));
        }

        return disciplinas;
    }

    // aux para tratamento de erros

    public boolean existeId(Integer id){
        if(this.disciplinaRepositorio.findById(id).isPresent())
            return true;
        return false;
    }

    public boolean existeProfessor(DisciplinaDTO disciplinaDTO){
        return this.professorServico.existeProfessor(disciplinaDTO);
    }
    
}
