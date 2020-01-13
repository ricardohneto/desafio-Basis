package br.com.basis.prova.recurso;

import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.servico.AlunoServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity<AlunoDTO> salvar(@Valid @RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        AlunoDTO result = this.alunoServico.salvar(alunoDTO);
        return ResponseEntity.created(new URI(API_ALUNOS + result.getId())).body(result);
    }

    @PutMapping
    public ResponseEntity<AlunoDTO> editar(@Valid @RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        AlunoDTO result = alunoServico.salvar(alunoDTO);
        return ResponseEntity.created(new URI(API_ALUNOS + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        alunoServico.excluir(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> consultar() {
        return ResponseEntity.ok(this.alunoServico.consultar());
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<AlunoDetalhadoDTO>> detalhar() {
        return ResponseEntity.ok(this.alunoServico.detalhar());
    }
}
