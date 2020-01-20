package br.com.basis.prova.recurso;

import br.com.basis.prova.dominio.AvaliacaoId;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import br.com.basis.prova.servico.AvaliacaoServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoRecurso {

    private static final String API_AVALIACOES = "/avaliacoes";

    private final AvaliacaoServico avaliacaoServico;

    public AvaliacaoRecurso(AvaliacaoServico avaliacaoServico) { this.avaliacaoServico = avaliacaoServico; }

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> salvar(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        AvaliacaoDTO result = this.avaliacaoServico.salvar(avaliacaoDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{idDisciplina}/{idAluno}")
    public ResponseEntity<?> excluir(@PathVariable("idDisciplina") Integer idDisciplina,
                                     @PathVariable("idAluno") Integer idAluno) {
        this.avaliacaoServico.excluir(new AvaliacaoId(idDisciplina, idAluno));
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> consultar() {
        return ResponseEntity.ok(this.avaliacaoServico.consultar());
    }
}
