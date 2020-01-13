package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfessorMapper.class})
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {

    @Override
    @Mapping(target = "idProfessor", source = "professor.matricula")
    DisciplinaDTO toDto(Disciplina disciplina);

    @Override
    @Mapping(target = "professor.matricula", source = "idProfessor")
    Disciplina toEntity(DisciplinaDTO disciplinaDTO);

}
