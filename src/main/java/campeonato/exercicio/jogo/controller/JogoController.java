package campeonato.exercicio.jogo.controller;

import campeonato.exercicio.jogo.domain.Jogo;
import campeonato.exercicio.jogo.request.JogoPostRequestBody;
import campeonato.exercicio.jogo.request.JogoPutRequestBody;
import campeonato.exercicio.jogo.service.JogoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("jogos")
public class JogoController {
    private final JogoService jogoService;
    public JogoController(JogoService jogoService) {this.jogoService = jogoService;}
    public JogoService getJogosService() {return jogoService;}
    @GetMapping
    public ResponseEntity<List<Jogo>> jogos(){
        return ResponseEntity.ok(getJogosService().listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Jogo> findById(@PathVariable Integer id){
        return ResponseEntity.ok(getJogosService().findByIdOrThrowBackBadRequestException(id));
    }
    @PostMapping
    public ResponseEntity<Jogo> save(@RequestBody JogoPostRequestBody jogoPostRequestBody){
        return ResponseEntity.ok(getJogosService().save(jogoPostRequestBody));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        getJogosService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody JogoPutRequestBody jogoPutRequestBody){
        getJogosService().replace(jogoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
