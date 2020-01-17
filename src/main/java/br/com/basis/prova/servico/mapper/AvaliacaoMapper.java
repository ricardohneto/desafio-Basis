package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DisciplinaMapper.class, AlunoMapper.class})
public interface AvaliacaoMapper extends EntityMapper<AvaliacaoDTO, Avaliacao> {

    @Override
    @Mapping(target = "idDisciplina", source = "disciplina.id")
    @Mapping(target = "idAluno",source = "aluno.id")
    AvaliacaoDTO toDto(Avaliacao avaliacao);

    @Override
    @Mapping(target = "disciplina.id", source = "idDisciplina")
    @Mapping(target = "aluno.id",source = "idAluno")
    Avaliacao toEntity(AvaliacaoDTO avaliacaoDTO);

}
