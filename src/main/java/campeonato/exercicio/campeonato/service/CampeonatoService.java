package campeonato.exercicio.campeonato.service;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.repository.CampeonatosRepository;
import campeonato.exercicio.campeonato.request.CampeonatoPostRequestBody;
import campeonato.exercicio.campeonato.request.CampeonatoPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CampeonatoService {
    private final CampeonatosRepository campeonatosRepository;

    public CampeonatosRepository getCampeonatosRepository() {
        return campeonatosRepository;
    }

    public Campeonato findByIdOrThrowBackBadRequestException(Integer id) {
        return getCampeonatosRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato não encontrado"));
    }

    public Campeonato save(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        SameId_Ano(campeonatoPostRequestBody);
        //SameTime(campeonatoPostRequestBody);
        return getCampeonatosRepository().save(Campeonato.builder().nome(campeonatoPostRequestBody.getNome())
                .ano(campeonatoPostRequestBody.getAno()).build());
    }

    public void delete(int id) {
        getCampeonatosRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }

    public void replace(CampeonatoPutRequestBody campeonatoPutRequestBody) {
        Campeonato campSalvo = findByIdOrThrowBackBadRequestException(campeonatoPutRequestBody.getId());
        Campeonato campeonato = Campeonato.builder().id(campSalvo.getId()).nome(campeonatoPutRequestBody.getNome())
                .ano(campeonatoPutRequestBody.getAno()).build();
        getCampeonatosRepository().save(campeonato);
    }

    public Campeonato SameId_Ano(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (getCampeonatosRepository().existsByNomeAndAno(campeonatoPostRequestBody.getNome(), campeonatoPostRequestBody.getAno())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já cadastrado no ano"+ campeonatoPostRequestBody.getAno()+"\n");
        }
        return null;
    }

    /*public void SameTime(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (getCampeonatosRepository().existsByTime(String.valueOf(campeonatoPostRequestBody.getTimes()))){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time já cadastrado no campeonato:"+ campeonatoPostRequestBody.getTimes()+"\n");
        }
    }*/

    public Page<Campeonato> listAll(Pageable pageable) {
        return getCampeonatosRepository().findAll(pageable);
    }

 /*   public void StatusCampeonatos(){
        if status=1  jogos.save
        else Bad_Request: Campeonato encerrado
    }

  */


}