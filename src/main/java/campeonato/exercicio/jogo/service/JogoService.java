package campeonato.exercicio.jogo.service;

import campeonato.exercicio.jogo.domain.Jogo;
import campeonato.exercicio.jogo.repository.JogoRepository;
import campeonato.exercicio.jogo.request.JogoPostRequestBody;
import campeonato.exercicio.jogo.request.JogoPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JogoService {
    private final JogoRepository jogoRepository;
    public JogoRepository getJogoRepository() {
        return jogoRepository;
    }
    public List<Jogo> listAll() {
        return getJogoRepository().findAll();
    }
    public Jogo findByIdOrThrowBackBadRequestException(Integer id) {
        return getJogoRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partida não encontrada"));
    }
    public Jogo save(JogoPostRequestBody jogoPostRequestBody) {
        SameTimesPartida(jogoPostRequestBody);
        return getJogoRepository().save(Jogo.builder().nomePart(jogoPostRequestBody.getNomePart())
                .timeMandante(jogoPostRequestBody.getTimeMandante()).timeVisitante(jogoPostRequestBody.getTimeVisitante())
                .campeonato(jogoPostRequestBody.getCampeonato()).build());
    }
    public void delete(int id) {
        getJogoRepository().delete(findByIdOrThrowBackBadRequestException(id));}
    public void replace(JogoPutRequestBody jogoPutRequestBody) {
        Jogo partidaSalva = findByIdOrThrowBackBadRequestException(jogoPutRequestBody.getId());
        Jogo part = Jogo.builder().id(partidaSalva.getId()).nomePart(jogoPutRequestBody.getNomePart())
                .timeMandante(jogoPutRequestBody.getTimeMandante()).timeVisitante(jogoPutRequestBody.getTimeVisitante())
                .campeonato(jogoPutRequestBody.getCampeonato()).build();
        getJogoRepository().save(part);
    }
    public void SameTimesPartida(JogoPostRequestBody jogoPostRequestBody){
        if (Objects.equals(jogoPostRequestBody.getTimeMandante(), jogoPostRequestBody.getTimeVisitante())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time iguais escalados, a partida não poderá ser realizada");
        }
    }
}
