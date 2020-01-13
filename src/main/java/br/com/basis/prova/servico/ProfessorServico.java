package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.*;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.mapper.ProfessorDetalhadoMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import br.com.basis.prova.servico.mapper.ProfessorViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfessorServico {

    private final ProfessorViewMapper professorViewMapper;
    private ProfessorRepositorio professorRepositorio;
    private ProfessorMapper professorMapper;
    private ProfessorDetalhadoMapper professorDetalhadoMapper;

    @Autowired
    private DisciplinaRepositorio disciplinaRepositorio;

    public ProfessorServico(ProfessorMapper professorMapper,
                            ProfessorRepositorio professorRepositorio,
                            ProfessorDetalhadoMapper professorDetalhadoMapper,
                            ProfessorViewMapper professorViewMapper) {
        this.professorMapper = professorMapper;
        this.professorDetalhadoMapper = professorDetalhadoMapper;
        this.professorRepositorio = professorRepositorio;
        this.professorViewMapper = professorViewMapper;
    }

    public Professor salvar(ProfessorDTO professorDto) {
        return this.professorRepositorio.save(this.professorMapper.toEntity(professorDto));
    }

    public boolean excluir(String matricula) {
        if(this.professorRepositorio.findByMatricula(matricula).getDisciplinas().isEmpty()
        || this.professorRepositorio.findByMatricula(matricula).getDisciplinas() == null){
            this.professorRepositorio.delete(this.professorRepositorio.findByMatricula(matricula));
            return true;
        }
        return false;
    }
    public ProfessorDTO consultaUm(String matricula) {
        return this.professorMapper.toDto(this.professorRepositorio.findByMatricula(matricula));
    }
    
    public List<ProfessorViewDTO> consultar() {
        return this.setProfessorToDto();
    }

    public List<ProfessorDetalhadoDTO> detalhar() {
        return this.setProfessorDetalhadoToDto();
    }

    public List<ProfessorViewDTO> setProfessorToDto(){
        List<ProfessorViewDTO> professores = this.professorViewMapper.toDto(this.professorRepositorio.findAll());

        professores.forEach(professor -> {
            professor.setIdade(LocalDate.now().getYear() - professor.getDataNascimento().getYear());
        });

        return professores;
    }

    public List<ProfessorDetalhadoDTO> setProfessorDetalhadoToDto(){
        List<ProfessorDetalhadoDTO> professoresDetalhados = this.professorDetalhadoMapper.toDto(this.professorRepositorio.findAll());


        for (ProfessorDetalhadoDTO professor : professoresDetalhados) {
            String nomeDisciplinas = "";
            List<Disciplina> disciplinas = professor.getDisciplinas();
            for (Disciplina disciplina : disciplinas) {
                if(disciplina.equals(disciplinas.get(disciplinas.size()-1)))
                    nomeDisciplinas += disciplina.getNome();
                else
                    nomeDisciplinas += disciplina.getNome() + ", ";
            }
            professor.setNomeDisciplinas(nomeDisciplinas);
            if(nomeDisciplinas.equals(""))
                professor.setNomeDisciplinas("Sem Disciplinas");
        }

        return professoresDetalhados;
    }

    // aux para tratamento de erros

    public boolean existeMatricula(String matricula){
        if(this.professorRepositorio.findByMatricula(matricula) != null)
            return true;
        return false;
    }

    // metodo p/ disciplina servico

    public Professor addProfessor(DisciplinaDTO disciplinaDto) {
        return this.professorRepositorio.findByMatricula(disciplinaDto.getIdProfessor());
    }

    public String findNomeProfessor(String matricula) {
        Professor professorDTO = this.professorRepositorio.findByMatricula(matricula);

        if(professorDTO != null)
            return professorDTO.getNome();
        return null;
    }

    public boolean existeProfessor(DisciplinaDTO disciplinaDto){
        if(this.professorRepositorio.existsByMatricula(disciplinaDto.getIdProfessor()))
            return true;
        return false;
    }

}
