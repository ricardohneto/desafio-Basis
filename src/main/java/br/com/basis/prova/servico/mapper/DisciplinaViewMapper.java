package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaViewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfessorMapper.class})
public interface DisciplinaViewMapper extends EntityMapper<DisciplinaViewDTO, Disciplina> {

    @Override
    DisciplinaViewDTO toDto(Disciplina disciplina);

    Disciplina toEntity(DisciplinaDTO disciplinaDTO);

}
