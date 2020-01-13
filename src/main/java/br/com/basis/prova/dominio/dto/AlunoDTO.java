package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.dominio.Disciplina;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class AlunoDTO {

    private String matricula;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;

}
