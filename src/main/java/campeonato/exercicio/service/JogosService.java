package campeonato.exercicio.service;

import campeonato.exercicio.domain.Jogos;
import campeonato.exercicio.repository.JogosRepository;
import campeonato.exercicio.request.JogosPostRequestBody;
import campeonato.exercicio.request.JogosPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JogosService {
    private final JogosRepository jogosRepository;
    public JogosRepository getJogosRepository() {
        return jogosRepository;
    }
    public List<Jogos> listAll() {
        return getJogosRepository().findAll();
    }
    public Jogos findByIdOrThrowBackBadRequestException(Integer id) {
        return getJogosRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partida não encontrada"));
    }
    public Jogos save(JogosPostRequestBody jogosPostRequestBody) {
        SameTimesPartida(jogosPostRequestBody);
        return getJogosRepository().save(Jogos.builder().nomePart(jogosPostRequestBody.getNomePart())
                .tipo((jogosPostRequestBody.getTipo())).timeMandante(jogosPostRequestBody.getTimeMandante())
                .timeVisitante(jogosPostRequestBody.getTimeVisitante()).status((jogosPostRequestBody.getStatus())).build());
    }
    public void delete(int id) {getJogosRepository().delete(findByIdOrThrowBackBadRequestException(id));}
    public void replace(JogosPutRequestBody jogosPutRequestBody) {
        Jogos partidaSalva = findByIdOrThrowBackBadRequestException(jogosPutRequestBody.getId());
        Jogos part = Jogos.builder().id(partidaSalva.getId()).nomePart(jogosPutRequestBody.getNomePart())
                .tipo((jogosPutRequestBody.getTipo())).timeMandante(jogosPutRequestBody.getTimeMandante())
                .timeVisitante(jogosPutRequestBody.getTimeVisitante()).status((jogosPutRequestBody.getStatus())).build();
        getJogosRepository().save(part);
    }
    public void SameTimesPartida(JogosPostRequestBody jogosPostRequestBody){
        if (Objects.equals(jogosPostRequestBody.getTimeMandante(), jogosPostRequestBody.getTimeVisitante())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Times iguais escalados, a partida não poderá ser realizada");
        }
    }
}
