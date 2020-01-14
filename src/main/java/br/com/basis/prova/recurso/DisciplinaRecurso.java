package br.com.basis.prova.recurso;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import br.com.basis.prova.servico.DisciplinaServico;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<DisciplinaDTO> salvar(@Valid @RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        DisciplinaDTO result = this.disciplinaServico.salvar(disciplinaDTO);
        return ResponseEntity.created(new URI(API_DISCIPLINAS + result.getId())).body(result);
    }

    @PutMapping
    public ResponseEntity<DisciplinaDTO> editar(@Valid @RequestBody DisciplinaDTO disciplinaDto) throws URISyntaxException{
        DisciplinaDTO result = this.disciplinaServico.salvar(disciplinaDto);
        return ResponseEntity.created(new URI(API_DISCIPLINAS + result.getId())).body(result);
    }

    @PutMapping("/matricular")
    public ResponseEntity<?> matricular(@RequestBody DisciplinaDTO disciplinaDTO,
                                        @PathVariable("matricula") String matricula) {

        this.disciplinaServico.matricular(disciplinaDTO, matricula);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Integer id) {
        this.disciplinaServico.excluir(id);
        return ResponseEntity.status(200).build();

    }

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> consultar() {
        return ResponseEntity.ok(disciplinaServico.consultar());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<DisciplinaDetalhadaDTO>> detalhar() {
        return ResponseEntity.ok(disciplinaServico.detalhar());
    }
}
