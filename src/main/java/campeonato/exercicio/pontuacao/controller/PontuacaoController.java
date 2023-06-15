package campeonato.exercicio.pontuacao.controller;

import campeonato.exercicio.pontuacao.domain.Pontuacao;
import campeonato.exercicio.pontuacao.service.PontuacaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("pontuacao")
@Log4j2
//controles
public class PontuacaoController {
    public PontuacaoController(PontuacaoService pontuacaoService) {
        this.pontuacaoService = pontuacaoService;
    }
    private final PontuacaoService pontuacaoService;
    public PontuacaoService getPontuacaoService() {
        return pontuacaoService;
    }
    @GetMapping
    public ResponseEntity<List<Pontuacao>> times(){
        return ResponseEntity.ok(getPontuacaoService().listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Pontuacao> findById(@PathVariable Long id){
        return ResponseEntity.ok(getPontuacaoService().findByIdOrThrowBackBadRequestException(id));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Pontuacao>> findByNomeTime(@RequestParam String nomeTime) {
        return ResponseEntity.ok(pontuacaoService.findByNomeTime(nomeTime));
    }

}