package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfessorMapper.class})
public interface DisciplinaDetalhadoMapper extends EntityMapper<DisciplinaDetalhadaDTO, Disciplina> {

    @Override
    @Mapping(target = "idProfessor", source = "professor.matricula")
    DisciplinaDetalhadaDTO toDto(Disciplina disciplina);

    @Override
    @Mapping(target = "professor.matricula", source = "idProfessor")
    Disciplina toEntity(DisciplinaDetalhadaDTO disciplinaDetalhadaDTO);

}