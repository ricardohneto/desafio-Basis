package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.*;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.servico.mapper.AlunoDetalhadoMapper;
import br.com.basis.prova.servico.mapper.AlunoMapper;
import br.com.basis.prova.servico.mapper.AlunoViewMapper;
import br.com.basis.prova.servico.mapper.DisciplinaViewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AlunoServico {

    private AlunoMapper alunoMapper;
    private AlunoDetalhadoMapper alunoDetalhadoMapper;
    private AlunoViewMapper alunoViewMapper;
    private AlunoRepositorio alunoRepositorio;
    private DisciplinaRepositorio disciplinaRepositorio;

    public AlunoServico(AlunoMapper alunoMapper,
                        AlunoDetalhadoMapper alunoDetalhadoMapper,
                        AlunoViewMapper alunoViewMapper,
                        AlunoRepositorio alunoRepositorio,
                        DisciplinaRepositorio disciplinaRepositorio) {
        this.alunoMapper = alunoMapper;
        this.alunoViewMapper = alunoViewMapper;
        this.alunoDetalhadoMapper = alunoDetalhadoMapper;
        this.alunoRepositorio = alunoRepositorio;
        this.disciplinaRepositorio = disciplinaRepositorio;
    }

    public Aluno salvar(AlunoDTO alunoDto) {
        return this.alunoRepositorio.save(this.alunoMapper.toEntity(alunoDto));
    }

    public boolean excluir(String matricula) {
        if(this.alunoRepositorio.findByMatricula(matricula).getDisciplinas().isEmpty()
                || this.alunoRepositorio.findByMatricula(matricula).getDisciplinas() == null){
            this.alunoRepositorio.delete(this.alunoRepositorio.findByMatricula(matricula));
            return true;
        }
        return false;
    }

    public AlunoDTO consultaUm(String matricula) {
        return this.alunoMapper.toDto(this.alunoRepositorio.findByMatricula(matricula));
    }

    public List<AlunoViewDTO> consultar() {
        return this.setAlunoToDto();
    }

    public List<AlunoDetalhadoDTO> detalhar() {
        return this.setAlunoDetalhadoToDto();
    }

    // metodos para transformar para DTO e atualizar a idade

    public List<AlunoViewDTO> setAlunoToDto(){
        List<AlunoViewDTO> alunosDto = this.alunoViewMapper.toDto(this.alunoRepositorio.findAll());

        alunosDto.forEach(alunoDto -> {
            alunoDto.setIdade(LocalDate.now().getYear() - alunoDto.getDataNascimento().getYear());
        });

        return alunosDto;
    }

    public List<AlunoDetalhadoDTO> setAlunoDetalhadoToDto(){
        List<AlunoDetalhadoDTO> alunosDetalhadosDto = this.alunoDetalhadoMapper.toDto(this.alunoRepositorio.findAll());

        for (AlunoDetalhadoDTO alunoDetalhadoDTO : alunosDetalhadosDto) {
            String nomeDisciplinas = "";
            alunoDetalhadoDTO.setIdade(LocalDate.now().getYear() - alunoDetalhadoDTO.getDataNascimento().getYear());

            Aluno aluno = this.alunoDetalhadoMapper.toEntity(alunoDetalhadoDTO);
            List<Disciplina> disciplinas = aluno.getDisciplinas();
            for (Disciplina disciplina : disciplinas) {
                if(disciplina.equals(disciplinas.get(disciplinas.size()-1)))
                    nomeDisciplinas += disciplina.getNome();
                else
                    nomeDisciplinas += disciplina.getNome() + ", ";
            }
            alunoDetalhadoDTO.setNomeDisciplinas(nomeDisciplinas);
            if(nomeDisciplinas.equals(""))
                alunoDetalhadoDTO.setNomeDisciplinas("Sem Disciplinas");
        }

        return alunosDetalhadosDto;
    }

    // aux para tratamento de erros

    public boolean existeMatricula(String matricula){
        if(this.alunoRepositorio.findByMatricula(matricula) != null)
            return true;
        return false;
    }

    // verificar se a materia está ativa

    public void onlyDisciplinasAtivas(AlunoDTO alunoDto){
        Aluno aluno = this.alunoMapper.toEntity(alunoDto);

        List<Disciplina> disciplinas = aluno.getDisciplinas();
        if (disciplinas == null)
            disciplinas = new ArrayList<>();
        List<Disciplina> disciplinasAtivas = new ArrayList<>();

        disciplinas.forEach(disciplina -> {
            if(disciplina.getAtiva() == 1)
                disciplinasAtivas.add(disciplina);
        });
        aluno.setDisciplinas(disciplinasAtivas);
    }

    // metodo p/ disciplina servico

    public Aluno addAluno() {
        return this.alunoRepositorio.findTopByOrderByIdDesc();
    }

}
