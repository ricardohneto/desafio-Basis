package br.com.basis.prova.recurso;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaViewDTO;
import br.com.basis.prova.servico.DisciplinaServico;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaRecurso {

    private static final String API_DISCIPLINAS = "/disciplinas";

    private final DisciplinaServico disciplinaServico;

    public DisciplinaRecurso(DisciplinaServico disciplinaServico) {
        this.disciplinaServico = disciplinaServico;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody DisciplinaDTO disciplinaDto) {

        ifNotExistsProfessor(disciplinaDto);

        this.disciplinaServico.salvar(disciplinaDto);
        return new ResponseEntity<>(disciplinaDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody DisciplinaDTO disciplinaDto,
                                    @PathVariable("id") Integer id) {

        ifNotExistsProfessor(disciplinaDto);

        disciplinaDto.setId(id);
        this.disciplinaServico.salvar(disciplinaDto);
        return new ResponseEntity<>(disciplinaDto, HttpStatus.OK);
    }

    @PutMapping("/matricular/{id}")
    public ResponseEntity<?> matricular(@PathVariable("id") Integer id) {

        this.disciplinaServico.matricular(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Integer id) {

        ifNotExistsId(id);

        this.disciplinaServico.excluir(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public DisciplinaDTO consultarUm(@PathVariable("id") Integer id){
        return this.disciplinaServico.consultaUm(id);
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaViewDTO>> consultar() {
        return ResponseEntity.ok(disciplinaServico.consultar());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<DisciplinaDetalhadaDTO>> detalhar() {
        return ResponseEntity.ok(disciplinaServico.detalhar());
    }

    // privates p/ tratamento de erros

    private void ifNotExistsId(Integer id){
        if(!this.disciplinaServico.existeId(id))
            throw new RegraNegocioException("Está Disciplina Não Existe");
    }

    private void ifNotExistsProfessor(DisciplinaDTO disciplinaDto){
        if(disciplinaDto.getIdProfessor() == null)
            throw new RegraNegocioException("Professor é Nulo");
        if(!this.disciplinaServico.existeProfessor(disciplinaDto))
            throw new RegraNegocioException("Este Professor Não Existe");
    }

}
