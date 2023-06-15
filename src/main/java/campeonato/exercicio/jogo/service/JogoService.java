package campeonato.exercicio.jogo.service;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.dto.CampeonatoDTO;
import campeonato.exercicio.campeonato.service.CampeonatoService;
import campeonato.exercicio.jogo.domain.Jogo;
import campeonato.exercicio.jogo.repository.JogoRepository;
import campeonato.exercicio.jogo.request.JogoPostRequestBody;
import campeonato.exercicio.jogo.request.JogoPutRequestBody;
import campeonato.exercicio.time.service.TimeService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<Jogo> listAll() {
        return getJogoRepository().findAll();
    }
    public Jogo findByIdOrThrowBackBadRequestException(Long id) {
        return getJogoRepository().findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partida não encontrada"));
    }
    @Transactional
    public Jogo save(JogoPostRequestBody jogoPostRequestBody) {
        sameTimesPartida(jogoPostRequestBody);
        Jogo newJogo = new Jogo();
        if (jogoPostRequestBody.getCampeonato()!=null){
            Campeonato campeonato = campeonatoService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getCampeonato());
            campeonatoService.validateIniciadoOuFinalizado(campeonato.getCampeonatoId());
            newJogo.setCampeonatoId(campeonato);
            validateExistsJogo(jogoPostRequestBody.getCampeonato(), jogoPostRequestBody.getTimeMandante()
                    , jogoPostRequestBody.getTimeVisitante());
        }
        newJogo.setGolsMand(0);
        newJogo.setGolsVisit(0);
        newJogo.setTimeMandante(timeService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getTimeMandante()));
        newJogo.setTimeVisitante(timeService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getTimeVisitante()));
        newJogo.setNomePart(timeService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getTimeMandante()).getNome()
                +" x "+timeService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getTimeVisitante()).getNome());
        newJogo.setStatusPartida(false);
        return getJogoRepository().save(newJogo);
    }

    public void delete(Long id) {
        getJogoRepository().delete(findByIdOrThrowBackBadRequestException(id));}
    @Transactional
    public void replace(JogoPutRequestBody jogoPutRequestBody) {
        Jogo partidaSalva = new Jogo();
        partidaSalva.setId(findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getId()).getId());
        if (jogoPutRequestBody.getCampeonato()!=null){
            Campeonato campeonato = campeonatoService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getCampeonato());
            campeonatoService.validateFinalizadoOuNaoIniciado(campeonato.getCampeonatoId());
            validateExistsJogo(jogoPutRequestBody.getCampeonato(), jogoPutRequestBody.getTimeMandante()
                    , jogoPutRequestBody.getTimeVisitante());
            partidaSalva.setCampeonatoId(campeonato);
            //atualizarPlacar(jogoPutRequestBody);   método só pra isso igual start?
        }
        if (jogoPutRequestBody.getTimeMandante() == jogoPutRequestBody.getTimeVisitante()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time iguais escalados, a partida não poderá ser realizada");
        }
        partidaSalva.setGolsMand(jogoPutRequestBody.getGolsMand());
        partidaSalva.setGolsVisit(jogoPutRequestBody.getGolsVisit());
        partidaSalva.setTimeMandante(timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeMandante()));
        partidaSalva.setTimeVisitante(timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeVisitante()));
        partidaSalva.setNomePart(timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeMandante()).getNome()
                +" x "+timeService.findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getTimeVisitante()).getNome());
        partidaSalva.setStatusPartida(jogoPutRequestBody.getStatusPartida());
        getJogoRepository().save(partidaSalva);

    }
    public void sameTimesPartida(JogoPostRequestBody jogoPostRequestBody){
        if (jogoPostRequestBody.getTimeMandante() == jogoPostRequestBody.getTimeVisitante()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time iguais escalados, a partida não poderá ser realizada");
        }
    }
    public void validateExistsJogo(Long campeonatoId, Long timeMandante, Long timeVisitante ) {
        if (jogoRepository.existsJogo(campeonatoId, timeMandante, timeVisitante)==true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partida já cadastrada neste Campeonato: time "+
                    timeMandante +" como time mandante e time "+ timeVisitante +" como time visitante");
        }
    }

}
