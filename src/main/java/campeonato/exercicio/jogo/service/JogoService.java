package campeonato.exercicio.jogo.service;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.service.CampeonatoService;
import campeonato.exercicio.jogo.domain.Jogo;
import campeonato.exercicio.jogo.repository.JogoRepository;
import campeonato.exercicio.jogo.request.JogoPostRequestBody;
import campeonato.exercicio.jogo.request.JogoPutRequestBody;
import campeonato.exercicio.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JogoService {
    private final JogoRepository jogoRepository;
    private final CampeonatoService campeonatoService;
    private final TimeService timeService;
    public JogoRepository getJogoRepository() {
        return jogoRepository;
    }
    public List<Jogo> listAll() {
        return getJogoRepository().findAll();
    }
    public Jogo findByIdOrThrowBackBadRequestException(Long id) {
        return getJogoRepository().findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partida não encontrada"));
    }
    public Jogo save(JogoPostRequestBody jogoPostRequestBody) {
        sameTimesPartida(jogoPostRequestBody);
        Jogo newJogo = new Jogo();
        if (jogoPostRequestBody.getCampeonato()!=null){
            Campeonato campeonato = campeonatoService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getCampeonato());
            campeonatoService.validateFinalizadoOuNaoIniciado(campeonato.getCampeonatoId());
            newJogo.setCampeonato(campeonato);
        }
        newJogo.setGolsMand(0);
        newJogo.setGolsVisit(0);
        newJogo.setTimeMandante(timeService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getTimeMandante()));
        newJogo.setTimeVisitante(timeService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getTimeVisitante()));
        newJogo.setNomePart(jogoPostRequestBody.getTimeMandante()+" x "+jogoPostRequestBody.getTimeVisitante());
        return getJogoRepository().save(newJogo);
    }

    public void delete(Long id) {
        getJogoRepository().delete(findByIdOrThrowBackBadRequestException(id));}
    public void replace(JogoPutRequestBody jogoPutRequestBody) {
        Jogo partidaSalva = new Jogo();
        partidaSalva.setId(findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getId()).getId());
        if (jogoPutRequestBody.getCampeonato()!=null){
            Campeonato campeonato = campeonatoService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getCampeonato());
            campeonatoService.validateFinalizadoOuNaoIniciado(campeonato.getCampeonatoId());
            partidaSalva.setCampeonato(campeonato);
        }
        if (jogoPutRequestBody.getTimeMandante() == jogoPutRequestBody.getTimeVisitante()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time iguais escalados, a partida não poderá ser realizada");
        }
        partidaSalva.setGolsMand(0);
        partidaSalva.setGolsVisit(0);
        partidaSalva.setTimeMandante(timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeMandante()));
        partidaSalva.setTimeVisitante(timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeVisitante()));
        partidaSalva.setNomePart(timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeMandante()).getNome()
                +" x "+timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeVisitante()).getNome());
        getJogoRepository().save(partidaSalva);
    }
    public void sameTimesPartida(JogoPostRequestBody jogoPostRequestBody){
        if (jogoPostRequestBody.getTimeMandante() == jogoPostRequestBody.getTimeVisitante()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time iguais escalados, a partida não poderá ser realizada");
        }
    }
}
