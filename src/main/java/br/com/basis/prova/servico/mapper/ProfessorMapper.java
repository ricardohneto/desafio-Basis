package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProfessorMapper extends EntityMapper<ProfessorDTO, Professor> {

    Professor toEntity(ProfessorDTO professorDTO);

    ProfessorDTO toDto(Professor professor);

}
