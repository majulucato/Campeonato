package campeonato.exercicio.controller;

import campeonato.exercicio.domain.Times;
import campeonato.exercicio.request.TimesPostRequestBody;
import campeonato.exercicio.request.TimesPutRequestBody;
import campeonato.exercicio.service.TimesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/times")
@Log4j2
//controles  //http://localhost:8080/times/
public class TimesController {
    public TimesController(TimesService timesService) {
        this.timesService = timesService;
    }
    public TimesService getTimesService() {
        return timesService;
    }
    private final TimesService timesService;

    @GetMapping
    public ResponseEntity<List<Times>> times(){
        return ResponseEntity.ok(getTimesService().listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Times> findById(@PathVariable int id){
        return ResponseEntity.ok(getTimesService().findByIdOrThrowBackBadRequestException(id));
    }
    @PostMapping
    public ResponseEntity<Times> save(@RequestBody TimesPostRequestBody timesPostRequestBody){
        return ResponseEntity.ok(getTimesService().save(timesPostRequestBody));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        getTimesService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TimesPutRequestBody timesPutRequestBody){
        getTimesService().replace(timesPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}