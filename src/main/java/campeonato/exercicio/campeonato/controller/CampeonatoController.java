package campeonato.exercicio.campeonato.controller;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.dto.CampeonatoDTO;
import campeonato.exercicio.campeonato.request.CampeonatoPostRequestBody;
import campeonato.exercicio.campeonato.request.CampeonatoPutRequestBody;
import campeonato.exercicio.campeonato.service.CampeonatoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("campeonatos")
public class CampeonatoController {
    private final CampeonatoService campeonatoService;
    public CampeonatoController(CampeonatoService campeonatoService) {this.campeonatoService = campeonatoService;}
    public CampeonatoService getCampeonatosService() {return campeonatoService;}

    @GetMapping //listar todos os campeonatos
    public ResponseEntity<Page<Campeonato>> campeonatosList(@PageableDefault(page=0, size=10,
    sort = "ano", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(campeonatoService.listAll(pageable));
    }
    @GetMapping(path = "/{id}")  //achar campeonato pelo id
    public ResponseEntity<Campeonato> findById(@PathVariable Long id){
        return ResponseEntity.ok(getCampeonatosService().findByIdOrThrowBackBadRequestException(id));
    }
    @PostMapping //criar novo campeonato
    public ResponseEntity<Campeonato> save(@RequestBody CampeonatoPostRequestBody campeonatoPostRequestBody){
        return ResponseEntity.ok(getCampeonatosService().save(campeonatoPostRequestBody));
    }
    @PostMapping(path = "/{id}") //adicionar times ao campeonato
    public ResponseEntity<CampeonatoDTO> addTimes(@RequestBody CampeonatoDTO campeonatoDTO){
        return ResponseEntity.ok(getCampeonatosService().addTimes(campeonatoDTO));
    }
    @DeleteMapping(path = "/{id}") //deletar um campeonato
    public ResponseEntity<Void> delete(@PathVariable Long id){
        getCampeonatosService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping (path = "/{id}") //atualizar dados de um campeonato
    public ResponseEntity<Campeonato> replace(@PathVariable Long id,
                                              @RequestBody @Validated CampeonatoPutRequestBody campeonatoPutRequestBody){
        getCampeonatosService().replace(campeonatoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "/{id}/start") //iniciar campeonato
    public ResponseEntity<Void> startCampeonato(@PathVariable Long id,
                                                @RequestBody @Validated CampeonatoDTO campeonatoDTO){
        campeonatoService.startCampeonato(campeonatoDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping(path = "/{id}/finish") //finalizar campeonato
    public ResponseEntity<Void> finishCampeonato(@PathVariable Long id){
        campeonatoService.finishCampeonato(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
