package br.com.basis.prova.repositorio;

import br.com.basis.prova.dominio.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepositorio extends JpaRepository<Professor, Integer>, JpaSpecificationExecutor<Professor> {

    Professor findByMatricula(String matricula);

}
