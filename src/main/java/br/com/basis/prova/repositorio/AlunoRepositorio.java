package br.com.basis.prova.repositorio;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Integer>, JpaSpecificationExecutor<Aluno> {

    Aluno findByCpf(String cpf);

    Aluno findByMatricula(String matricula);

    List<Aluno> findByDisciplinas(Disciplina disciplina);
}
