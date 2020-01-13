package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AlunoDetalhadoMapper extends EntityMapper<AlunoDetalhadoDTO, Aluno> {

    Aluno toEntity(AlunoDetalhadoDTO AlunoDetalhadoDTO);

    AlunoDetalhadoDTO toDto(Aluno aluno);


}
