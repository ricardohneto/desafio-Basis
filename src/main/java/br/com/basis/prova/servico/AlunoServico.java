package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.*;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoDetalhadoMapper;
import br.com.basis.prova.servico.mapper.AlunoMapper;
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
    private AlunoRepositorio alunoRepositorio;
    private DisciplinaRepositorio disciplinaRepositorio;

    public AlunoServico(AlunoMapper alunoMapper,
                        AlunoDetalhadoMapper alunoDetalhadoMapper,
                        AlunoRepositorio alunoRepositorio,
                        DisciplinaRepositorio disciplinaRepositorio) {
        this.alunoMapper = alunoMapper;
        this.alunoDetalhadoMapper = alunoDetalhadoMapper;
        this.alunoRepositorio = alunoRepositorio;
        this.disciplinaRepositorio = disciplinaRepositorio;
    }

    public AlunoDTO salvar(AlunoDTO alunoDto) {
        Aluno aluno = this.alunoMapper.toEntity(alunoDto);

        if(verificarCPF(aluno))
            throw new RegraNegocioException("CPF Já Existe");

        if(verificarMatricula(aluno))
            throw new RegraNegocioException("Matricula Já Existe");

        return this.alunoMapper.toDto(this.alunoRepositorio.save(aluno));
    }

    private boolean verificarCPF(Aluno aluno){
        Aluno alunoCpf = this.alunoRepositorio.findByCpf(aluno.getCpf());
        return !(alunoCpf == null || alunoCpf.getId().equals(aluno.getId()));
    }

    private boolean verificarMatricula(Aluno aluno){
        Aluno alunoMatricula = this.alunoRepositorio.findByMatricula(aluno.getMatricula());
        return !(alunoMatricula == null || alunoMatricula.getId().equals(aluno.getId()));
    }

    public void excluir(String matricula) {
        Aluno aluno = this.alunoRepositorio.findByMatricula(matricula);

        if(aluno == null)
            throw new RegraNegocioException("Matricula Não Encontrada");

        if (!(disciplinaRepositorio.findAllByAtivaAndAlunos(1, aluno).isEmpty()))
            throw new RegraNegocioException("Aluno Matriculado em Disciplinas");

        this.alunoRepositorio.delete(aluno);
    }

    public List<AlunoDTO> consultar() {
        List<AlunoDTO> alunos = this.alunoMapper.toDto(this.alunoRepositorio.findAll());
        preencherIdades(alunos);
        return alunos;
    }

    private void preencherIdades(List<AlunoDTO> alunos) {
        alunos.forEach(alunoDTO -> {
            alunoDTO.setIdade(LocalDate.now().getYear() - alunoDTO.getDataNascimento().getYear());
        });
    }

    public List<AlunoDetalhadoDTO> detalhar() {
        List<AlunoDetalhadoDTO> alunos = this.alunoDetalhadoMapper.toDto(this.alunoRepositorio.findAll());
        preencherIdadesAndDisciplinas(alunos);
        return alunos;
    }

    private void preencherIdadesAndDisciplinas(List<AlunoDetalhadoDTO> alunos) {
        alunos.forEach(alunoDetalhado -> {
            List<String> nomeDisciplinas = new ArrayList<>();
            alunoDetalhado.setIdade(LocalDate.now().getYear() - alunoDetalhado.getDataNascimento().getYear());
            List<DisciplinaDTO> disciplinasDTO = alunoDetalhado.getDisciplinas();
            for (DisciplinaDTO disciplinaDTO : disciplinasDTO) {
                if (disciplinaDTO.getAtiva() == 1)
                    nomeDisciplinas.add(disciplinaDTO.getNome());
            }
            alunoDetalhado.setNomeDisciplinas(nomeDisciplinas);
        });
    }

    // metodo para classe AvaliacaoServico

    protected Aluno addAluno(Integer id) {
        Aluno aluno = this.alunoRepositorio.findById(id).orElseThrow(() ->
                new RegraNegocioException("Aluno Não Encontrado"));

        return aluno;
    }

}
