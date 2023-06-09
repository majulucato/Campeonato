package campeonato.exercicio.campeonato.controller;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.request.CampeonatoPostRequestBody;
import campeonato.exercicio.campeonato.request.CampeonatoPutRequestBody;
import campeonato.exercicio.campeonato.service.CampeonatoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        findById(campeonatoPostRequestBody.getId());//verificar condições
        return ResponseEntity.ok(getCampeonatosService().save(campeonatoPostRequestBody));
    }
    @DeleteMapping(path = "/{id}") //deletar um campeonato
    public ResponseEntity<Void> delete(@PathVariable Long id){
        getCampeonatosService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping //atualizar dados de um campeonato
    public ResponseEntity<Void> replace(@RequestBody CampeonatoPutRequestBody campeonatoPutRequestBody){
        getCampeonatosService().replace(campeonatoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
