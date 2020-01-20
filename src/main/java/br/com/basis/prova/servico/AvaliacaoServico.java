package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.AvaliacaoId;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import br.com.basis.prova.repositorio.AvaliacaoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AvaliacaoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AvaliacaoServico {

    private AvaliacaoMapper avaliacaoMapper;
    private AvaliacaoRepositorio avaliacaoRepositorio;

    private DisciplinaServico disciplinaServico;
    private AlunoServico alunoServico;

    public AvaliacaoServico(AvaliacaoMapper avaliacaoMapper,
                            AvaliacaoRepositorio avaliacaoRepositorio,
                            DisciplinaServico disciplinaServico,
                            AlunoServico alunoServico) {
        this.avaliacaoMapper = avaliacaoMapper;
        this.avaliacaoRepositorio = avaliacaoRepositorio;
        this.disciplinaServico = disciplinaServico;
        this.alunoServico = alunoServico;
    }

    public AvaliacaoDTO salvar(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = this.avaliacaoMapper.toEntity(avaliacaoDTO);

        avaliacao.setDisciplina(this.disciplinaServico.addDisciplina(avaliacaoDTO.getIdDisciplina()));
        avaliacao.setAluno(this.alunoServico.addAluno(avaliacaoDTO.getIdAluno()));
        avaliacao.setId(new AvaliacaoId(avaliacaoDTO.getIdDisciplina(), avaliacaoDTO.getIdAluno()));

        return this.avaliacaoMapper.toDto(this.avaliacaoRepositorio.save(avaliacao));
    }

    public void excluir(AvaliacaoId avaliacaoId){
        Avaliacao avaliacao = this.avaliacaoRepositorio.findById(avaliacaoId).orElseThrow(() ->
                new RegraNegocioException("Avaliação Não Encontrada"));

        avaliacao.setAluno(null);
        avaliacao.setDisciplina(null);

        this.avaliacaoRepositorio.delete(avaliacao);
    }

    public List<AvaliacaoDTO> consultar() {
        List<AvaliacaoDTO> avaliacoes = this.avaliacaoMapper.toDto(this.avaliacaoRepositorio.findAll());
        preencherNomesAlunosAndDisciplinas(avaliacoes);
        return avaliacoes;
    }

    private void preencherNomesAlunosAndDisciplinas(List<AvaliacaoDTO> avaliacoes){
        avaliacoes.forEach(avaliacao -> {
            avaliacao.setNomeDisciplina(this.disciplinaServico.addDisciplina(avaliacao.getIdDisciplina()).getNome());
            avaliacao.setNomeAluno(this.alunoServico.addAluno(avaliacao.getIdAluno()).getNome());
        });

    }

}
