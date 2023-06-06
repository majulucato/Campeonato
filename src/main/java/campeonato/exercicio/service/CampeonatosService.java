package campeonato.exercicio.service;

import campeonato.exercicio.domain.Campeonatos;
import campeonato.exercicio.repository.CampeonatosRepository;
import campeonato.exercicio.request.CampeonatoPostRequestBody;
import campeonato.exercicio.request.CampeonatoPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampeonatosService {
    private final CampeonatosRepository campeonatosRepository;

    public CampeonatosRepository getCampeonatosRepository() {
        return campeonatosRepository;
    }

    public Campeonatos findByIdOrThrowBackBadRequestException(Integer id) {
        return getCampeonatosRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato não encontrado"));
    }

    public Campeonatos save(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        SameId_Ano(campeonatoPostRequestBody);
        SameTime(campeonatoPostRequestBody);
        return getCampeonatosRepository().save(Campeonatos.builder().nome(campeonatoPostRequestBody.getNome())
                .ano(campeonatoPostRequestBody.getAno())
                .status(campeonatoPostRequestBody.getStatus()).build());
    }//.times(campeonatoPostRequestBody.getTimes())

    public void delete(int id) {
        getCampeonatosRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }

    public void replace(CampeonatoPutRequestBody campeonatoPutRequestBody) {
        Campeonatos campSalvo = findByIdOrThrowBackBadRequestException(campeonatoPutRequestBody.getId());
        Campeonatos campeonatos = Campeonatos.builder().id(campSalvo.getId()).nome(campeonatoPutRequestBody.getNome()).ano(campeonatoPutRequestBody.getAno())
                .timeMand(campeonatoPutRequestBody.getTimeMand())
                .timeVisit(campeonatoPutRequestBody.getTimeVisit()).status(campeonatoPutRequestBody.getStatus()).build();
        getCampeonatosRepository().save(campeonatos);
    }

    public Campeonatos SameId_Ano(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (getCampeonatosRepository().existsByNomeAndAno(campeonatoPostRequestBody.getNome(), campeonatoPostRequestBody.getAno())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já cadastrado no ano"+ campeonatoPostRequestBody.getAno()+"\n");
        }
        return null;
    }

    public void SameTime(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (getCampeonatosRepository().existsByTime(String.valueOf(campeonatoPostRequestBody.getTimes()))){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time já cadastrado no campeonato:"+ campeonatoPostRequestBody.getTimes()+"\n");
        }
    }

    public Page<Campeonatos> listAll(Pageable pageable) {
        return getCampeonatosRepository().findAll(pageable);
    }

 /*   public void StatusCampeonatos(){
        if status=1  jogos.save
        else Bad_Request: Campeonato encerrado
    }

  */


}