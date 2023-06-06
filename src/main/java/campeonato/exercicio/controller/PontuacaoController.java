package campeonato.exercicio.controller;

import campeonato.exercicio.domain.Pontuacao;
import campeonato.exercicio.request.PontuacaoPostRequestBody;
import campeonato.exercicio.request.PontuacaoPutRequestBody;
import campeonato.exercicio.service.PontuacaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Pontuacao> findById(@PathVariable int id){
        return ResponseEntity.ok(getPontuacaoService().findByIdOrThrowBackBadRequestException(id));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Pontuacao>> findByNomeTime(@RequestParam String nomeTime) {
        return ResponseEntity.ok(pontuacaoService.findByNomeTime(nomeTime));
    }
    @PostMapping
    public ResponseEntity<Pontuacao> save(@RequestBody PontuacaoPostRequestBody pontuacaoPostRequestBody){
        return ResponseEntity.ok(getPontuacaoService().save(pontuacaoPostRequestBody));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        getPontuacaoService().deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        getPontuacaoService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody PontuacaoPutRequestBody pontuacaoPutRequestBody){
        getPontuacaoService().replace(pontuacaoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}