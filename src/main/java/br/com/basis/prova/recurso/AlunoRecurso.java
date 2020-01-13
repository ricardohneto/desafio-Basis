package br.com.basis.prova.recurso;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.dominio.dto.AlunoViewDTO;
import br.com.basis.prova.servico.AlunoServico;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/alunos")
public class AlunoRecurso {

    private static final String API_ALUNOS = "/alunos";

    private final AlunoServico alunoServico;

    public AlunoRecurso(AlunoServico alunoServico) {
        this.alunoServico = alunoServico;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> salvar(@RequestBody AlunoDTO aluno) throws RegraNegocioException{

        ifExistsMatricula(aluno.getMatricula());

        this.alunoServico.onlyDisciplinasAtivas(aluno);

        this.alunoServico.salvar(aluno);
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<?> editar(@RequestBody AlunoDTO alunoDto,
                                    @PathVariable("matricula") String matricula) {

        ifExistsMatricula(alunoDto.getMatricula());

        this.alunoServico.onlyDisciplinasAtivas(alunoDto);

        alunoDto.setMatricula(matricula);
        this.alunoServico.salvar(alunoDto);
        return new ResponseEntity<>(alunoDto, HttpStatus.OK);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<?> excluir(@PathVariable("matricula") String matricula) {

        ifNotExistsMatricula(matricula);

        if(this.alunoServico.excluir(matricula))
            return new ResponseEntity<>(HttpStatus.OK);
        throw new RegraNegocioException("Aluno Vinculado a Disciplinas");

    }

    @GetMapping("/{matricula}")
    public AlunoDTO consultarUm(@PathVariable("matricula") String matricula){
        return this.alunoServico.consultaUm(matricula);
    }

    @GetMapping
    public ResponseEntity<List<AlunoViewDTO>> consultar() {
        return ResponseEntity.ok(this.alunoServico.consultar());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<AlunoDetalhadoDTO>> detalhar() {
        return ResponseEntity.ok(this.alunoServico.detalhar());
    }

    // privates p/ tratamento de erros

    private void ifExistsMatricula(String matricula){
        if(this.alunoServico.existeMatricula(matricula))
            throw new RegraNegocioException("Está Matricula Já Existe");
    }

    private void ifNotExistsMatricula(String matricula){
        if(!this.alunoServico.existeMatricula(matricula))
            throw new RegraNegocioException("Está Matricula Não Existe");
    }
}
