package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.DisciplinaViewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {

    Aluno toEntity(AlunoDTO alunoDTO);

    AlunoDTO toDto(Aluno aluno);

}
