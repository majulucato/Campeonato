package campeonato.exercicio.controller;

import campeonato.exercicio.domain.Jogos;
import campeonato.exercicio.request.JogosPostRequestBody;
import campeonato.exercicio.request.JogosPutRequestBody;
import campeonato.exercicio.service.JogosService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("jogos")
@Log4j2
public class JogosController {
    private final JogosService jogosService;
    public JogosController(JogosService jogosService) {this.jogosService = jogosService;}
    public JogosService getJogosService() {return jogosService;}
    @GetMapping
    public ResponseEntity<List<Jogos>> jogos(){
        return ResponseEntity.ok(getJogosService().listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Jogos> findById(@PathVariable Integer id){
        return ResponseEntity.ok(getJogosService().findByIdOrThrowBackBadRequestException(id));
    }
    @PostMapping
    public ResponseEntity<Jogos> save(@RequestBody JogosPostRequestBody jogosPostRequestBody){
        return ResponseEntity.ok(getJogosService().save(jogosPostRequestBody));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        getJogosService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody JogosPutRequestBody jogosPutRequestBody){
        getJogosService().replace(jogosPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
