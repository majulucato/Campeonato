package campeonato.exercicio.jogo.service;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.service.CampeonatoService;
import campeonato.exercicio.jogo.domain.Jogo;
import campeonato.exercicio.jogo.repository.JogoRepository;
import campeonato.exercicio.jogo.request.JogoPostRequestBody;
import campeonato.exercicio.jogo.request.JogoPutRequestBody;
import campeonato.exercicio.jogo.request.UpdatePartida;
import campeonato.exercicio.pontuacao.domain.Pontuacao;
import campeonato.exercicio.pontuacao.repository.PontuacaoRepository;
import campeonato.exercicio.pontuacao.service.PontuacaoService;
import campeonato.exercicio.time.service.TimeService;
import lombok.RequiredArgsConstructor;
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
    private final PontuacaoService pontuacaoService;
    private final PontuacaoRepository pontuacaoRepository;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato com escala de jogos já existente");
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
    @Transactional
    public void updatePartida(UpdatePartida updatePartida) {
        Jogo update = findByIdOrThrowBackBadRequestException(updatePartida.getId());
        validatePartidaFinalizada(update);
        update.setId(update.getId());
        update.setNomePart(update.getNomePart());
        update.setCampeonatoId(update.getCampeonatoId());
        update.setTimeVisitante(update.getTimeVisitante());
        update.setTimeMandante(update.getTimeMandante());
        update.setGolsMand(updatePartida.getGolsMand());
        update.setGolsVisit(updatePartida.getGolsVisit());
        update.setStatusPartida(true);
        jogoRepository.save(update);
        winLoseOrDraw(update);
    }

    private void validatePartidaFinalizada(Jogo jogo) {
        if (jogo.getStatusPartida()==true){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel inserir pontuação para partida já finalizada");
        }
    }

    public void winLoseOrDraw(Jogo jogo){
        Pontuacao mandante = pontuacaoService.findByCampeonatoAndTime(jogo.getCampeonatoId().getCampeonatoId(),
                jogo.getTimeMandante().getTimeId());
        Pontuacao visitante = pontuacaoService.findByCampeonatoAndTime(jogo.getCampeonatoId().getCampeonatoId(),
                jogo.getTimeVisitante().getTimeId());
        mandante.setQuantGolsFeitos(mandante.getQuantGolsFeitos()+jogo.getGolsMand());
        mandante.setQuantGolsSofridos(mandante.getQuantGolsSofridos()+jogo.getGolsVisit());
        visitante.setQuantGolsFeitos(visitante.getQuantGolsFeitos()+jogo.getGolsVisit());
        visitante.setQuantGolsSofridos(visitante.getQuantGolsSofridos()+jogo.getGolsMand());
        if (jogo.getGolsMand() > jogo.getGolsVisit()){
            timeWins(mandante);
            timeLoses(visitante);
        }
        if (jogo.getGolsMand() == jogo.getGolsVisit()){
            gameDraws(mandante);
            gameDraws(visitante);
        }
        if (jogo.getGolsMand() < jogo.getGolsVisit()){
            timeWins(visitante);
            timeLoses(mandante);
        }
    }
    public void timeWins(Pontuacao pontuacao){
        Pontuacao update = pontuacaoService.findByCampeonatoAndTime(pontuacao.getCampeonatoId().getCampeonatoId(),
                pontuacao.getTimeId().getTimeId());
        update.setId(update.getId());
        update.setCampeonatoId(update.getCampeonatoId());
        update.setTimeId(pontuacao.getTimeId());
        update.setPont((update.getQuantVitorias()*3)+(update.getQuantEmpates()*1)+(update.getQuantDerrotas()*0));
        update.setQuantJogos(update.getQuantVitorias()+update.getQuantEmpates()+update.getQuantDerrotas());
        update.setQuantVitorias(update.getQuantVitorias()+1);
        update.setQuantEmpates(pontuacao.getQuantEmpates());
        update.setQuantDerrotas(pontuacao.getQuantDerrotas());
        update.setQuantGolsFeitos(pontuacao.getQuantGolsFeitos());
        update.setQuantGolsSofridos(pontuacao.getQuantGolsSofridos());
        pontuacaoRepository.save(update);
    }
    public void timeLoses(Pontuacao pontuacao){
        Pontuacao update = pontuacaoService.findByCampeonatoAndTime(pontuacao.getCampeonatoId().getCampeonatoId(),
                pontuacao.getTimeId().getTimeId());
        update.setId(update.getId());
        update.setCampeonatoId(update.getCampeonatoId());
        update.setTimeId(pontuacao.getTimeId());
        update.setPont((update.getQuantVitorias()*3)+(update.getQuantEmpates()*1)+(update.getQuantDerrotas()*0));
        update.setQuantJogos(update.getQuantVitorias()+update.getQuantEmpates()+update.getQuantDerrotas());
        update.setQuantVitorias(update.getQuantVitorias());
        update.setQuantEmpates(pontuacao.getQuantEmpates());
        update.setQuantDerrotas(pontuacao.getQuantDerrotas()+1);
        update.setQuantGolsFeitos(pontuacao.getQuantGolsFeitos());
        update.setQuantGolsSofridos(pontuacao.getQuantGolsSofridos());
        pontuacaoRepository.save(update);
    }
    public void gameDraws(Pontuacao pontuacao){
        Pontuacao update = pontuacaoService.findByCampeonatoAndTime(pontuacao.getCampeonatoId().getCampeonatoId(),
                pontuacao.getTimeId().getTimeId());
        update.setId(update.getId());
        update.setCampeonatoId(update.getCampeonatoId());
        update.setTimeId(pontuacao.getTimeId());
        update.setPont((update.getQuantVitorias()*3)+(update.getQuantEmpates()*1)+(update.getQuantDerrotas()*0));
        update.setQuantJogos(update.getQuantVitorias()+update.getQuantEmpates()+update.getQuantDerrotas());
        update.setQuantVitorias(update.getQuantVitorias());
        update.setQuantEmpates(pontuacao.getQuantEmpates()+1);
        update.setQuantDerrotas(pontuacao.getQuantDerrotas());
        update.setQuantGolsFeitos(pontuacao.getQuantGolsFeitos());
        update.setQuantGolsSofridos(pontuacao.getQuantGolsSofridos());
        pontuacaoRepository.save(update);
    }
}
