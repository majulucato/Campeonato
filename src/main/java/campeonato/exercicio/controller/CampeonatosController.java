package campeonato.exercicio.controller;

import campeonato.exercicio.domain.Campeonatos;
import campeonato.exercicio.request.CampeonatoPostRequestBody;
import campeonato.exercicio.request.CampeonatoPutRequestBody;
import campeonato.exercicio.service.CampeonatosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("campeonatos")
public class CampeonatosController {
    private final CampeonatosService campeonatosService;
    public CampeonatosController(CampeonatosService campeonatosService) {this.campeonatosService = campeonatosService;}
    public CampeonatosService getCampeonatosService() {return campeonatosService;}

    @GetMapping //listar todos os campeonatos
    public ResponseEntity<Page<Campeonatos>> campeonatosList(@PageableDefault(page=0, size=10,
    sort = "ano", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(campeonatosService.listAll(pageable));
    }
    @GetMapping(path = "/{id}")  //achar campeonato pelo id
    public ResponseEntity<Campeonatos> findById(@PathVariable Integer id){
        return ResponseEntity.ok(getCampeonatosService().findByIdOrThrowBackBadRequestException(id));
    }
    @PostMapping //criar novo campeonato
    public ResponseEntity<Campeonatos> save(@RequestBody CampeonatoPostRequestBody campeonatoPostRequestBody){
        findById(campeonatoPostRequestBody.getId());//verificar condições
        return ResponseEntity.ok(getCampeonatosService().save(campeonatoPostRequestBody));
    }
    @DeleteMapping(path = "/{id}") //deletar um campeonato
    public ResponseEntity<Void> delete(@PathVariable int id){
        getCampeonatosService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping //atualizar dados de um campeonato
    public ResponseEntity<Void> replace(@RequestBody CampeonatoPutRequestBody campeonatoPutRequestBody){
        getCampeonatosService().replace(campeonatoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
