package campeonato.exercicio.jogo.service;

import campeonato.exercicio.campeonato.service.CampeonatoService;
import campeonato.exercicio.jogo.domain.Jogo;
import campeonato.exercicio.jogo.repository.JogoRepository;
import campeonato.exercicio.jogo.request.JogoPostRequestBody;
import campeonato.exercicio.jogo.request.JogoPutRequestBody;
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
        if (jogoPostRequestBody.getCampeonato()!=null){
            campeonatoService.findByIdOrThrowBackBadRequestException(jogoPostRequestBody.getId());
            campeonatoService.validateFinalizadoOuNaoIniciado(jogoPostRequestBody.getCampeonato().getCampeonatoId());
        }
        jogoPostRequestBody.setNomePart(jogoPostRequestBody.getTimeMandante()+" x "+jogoPostRequestBody.getTimeVisitante());
        jogoPostRequestBody.setGolsMand(0);
        jogoPostRequestBody.setGolsVisit(0);
        return getJogoRepository().save(Jogo.builder()
                .nomePart(jogoPostRequestBody.getNomePart()).golsMand(jogoPostRequestBody.getGolsMand()).golsVisit(jogoPostRequestBody.getGolsVisit())
                .timeMandante(jogoPostRequestBody.getTimeMandante()).timeVisitante(jogoPostRequestBody.getTimeVisitante())
                .campeonato(jogoPostRequestBody.getCampeonato()).build());
    }
    public void delete(Long id) {
        getJogoRepository().delete(findByIdOrThrowBackBadRequestException(id));}
    public void replace(JogoPutRequestBody jogoPutRequestBody) {
        Jogo partidaSalva = findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getId());
        jogoPutRequestBody.setNomePart(jogoPutRequestBody.getTimeMandante()+" x "+jogoPutRequestBody.getTimeVisitante());
        Jogo part = Jogo.builder().id(partidaSalva.getId())
                .nomePart(jogoPutRequestBody.getNomePart()).golsMand(jogoPutRequestBody.getGolsMand()).golsVisit(jogoPutRequestBody.getGolsVisit())
                .timeMandante(jogoPutRequestBody.getTimeMandante()).timeVisitante(jogoPutRequestBody.getTimeVisitante())
                .campeonato(jogoPutRequestBody.getCampeonato()).build();
        getJogoRepository().save(part);
    }
    public void sameTimesPartida(JogoPostRequestBody jogoPostRequestBody){
        if (jogoPostRequestBody.getTimeMandante() == jogoPostRequestBody.getTimeVisitante()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time iguais escalados, a partida não poderá ser realizada");
        }
    }
}
