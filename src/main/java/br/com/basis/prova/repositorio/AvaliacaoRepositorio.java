package br.com.basis.prova.repositorio;

import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.AvaliacaoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepositorio extends JpaRepository<Avaliacao, AvaliacaoId> {

    @Override
    Optional<Avaliacao> findById(AvaliacaoId avaliacaoId);

}
