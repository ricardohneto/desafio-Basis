package br.com.basis.prova.recurso;



import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.servico.ProfessorServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ProfessorDTO> salvar(@Valid @RequestBody ProfessorDTO professorDto) throws URISyntaxException {
        ProfessorDTO result = this.professorServico.salvar(professorDto);
        return ResponseEntity.created(new URI(API_PROFESSORES + result.getId())).body(result);
    }

    @PutMapping
    public ResponseEntity<?> editar(@Valid @RequestBody ProfessorDTO professorDto) throws URISyntaxException{
        ProfessorDTO result = this.professorServico.salvar(professorDto);
        return ResponseEntity.created(new URI(API_PROFESSORES + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Integer id) {
        this.professorServico.excluir(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> consultar() {
        return ResponseEntity.ok(professorServico.consultar());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<ProfessorDetalhadoDTO>> detalhar() {
        return ResponseEntity.ok(professorServico.detalhar());
    }

}
