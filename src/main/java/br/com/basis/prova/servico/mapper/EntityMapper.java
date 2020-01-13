package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */
public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);
    List <D> toDto(List<E> entityList);
}