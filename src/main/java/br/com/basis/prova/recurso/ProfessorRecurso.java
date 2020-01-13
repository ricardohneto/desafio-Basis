package br.com.basis.prova.recurso;


import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.dominio.dto.ProfessorViewDTO;
import br.com.basis.prova.servico.ProfessorServico;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/professores")
public class ProfessorRecurso {

    private static final String API_PROFESSORES = "/professores";

    @Autowired
    private final ProfessorServico professorServico;

    public ProfessorRecurso(ProfessorServico professorServico) {
        this.professorServico = professorServico;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ProfessorDTO professorDto) {

        ifExistsMatricula(professorDto.getMatricula());

        professorServico.salvar(professorDto);
        return new ResponseEntity<>(professorDto, HttpStatus.OK);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<?> editar(@RequestBody ProfessorDTO professorDto,
                                    @PathVariable("matricula") String matricula) {

        ifExistsMatricula(professorDto.getMatricula());

        professorDto.setMatricula(matricula);
        this.professorServico.salvar(professorDto);
        return new ResponseEntity<>(professorDto, HttpStatus.OK);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<?> excluir(@PathVariable("matricula") String matricula) {

        ifNotExistsMatricula(matricula);

        if(this.professorServico.excluir(matricula))
            return new ResponseEntity<>(HttpStatus.OK);
        throw new RegraNegocioException("Professor Responsavel por Disciplinas");

    }

    @GetMapping("/{matricula}")
    public ProfessorDTO consultarUm(@PathVariable("matricula") String matricula){
        return this.professorServico.consultaUm(matricula);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorViewDTO>> consultar() {
        return ResponseEntity.ok(professorServico.consultar());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<ProfessorDetalhadoDTO>> detalhar() {
        return ResponseEntity.ok(professorServico.detalhar());
    }

    // privates p/ tratamento de erros

    private void ifExistsMatricula(String matricula){
        if(this.professorServico.existeMatricula(matricula))
            throw new RegraNegocioException("Está Matricula Já Existe");
    }

    private void ifNotExistsMatricula(String matricula){
        if(!this.professorServico.existeMatricula(matricula))
            throw new RegraNegocioException("Está Matricula Não Existe");
    }

}
