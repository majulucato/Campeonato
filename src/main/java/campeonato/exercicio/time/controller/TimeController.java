package campeonato.exercicio.time.controller;

import campeonato.exercicio.time.domain.Time;
import campeonato.exercicio.time.request.TimePostRequestBody;
import campeonato.exercicio.time.request.TimePutRequestBody;
import campeonato.exercicio.time.service.TimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/times")
@Log4j2
//controles  //http://localhost:8080/times/
public class TimeController {
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }
    public TimeService getTimesService() {
        return timeService;
    }
    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<Page<Time>> times(@PageableDefault(page=0, size=10,
            sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(timeService.listAll(pageable));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Time> findById(@PathVariable long id){
        return ResponseEntity.ok(getTimesService().findByIdOrThrowBackBadRequestException(id));
    }
    @PostMapping
    public ResponseEntity<Time> save(@RequestBody TimePostRequestBody timePostRequestBody){
        return ResponseEntity.ok(getTimesService().save(timePostRequestBody));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        getTimesService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TimePutRequestBody timePutRequestBody){
        getTimesService().replace(timePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}