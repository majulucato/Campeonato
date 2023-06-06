package campeonato.exercicio.service;

import campeonato.exercicio.domain.Pontuacao;
import campeonato.exercicio.repository.PontuacaoRepository;
import campeonato.exercicio.request.PontuacaoPostRequestBody;
import campeonato.exercicio.request.PontuacaoPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
//regras de negócio
@Service
@RequiredArgsConstructor
public class PontuacaoService {
    private final PontuacaoRepository pontuacaoRepository;
    public PontuacaoRepository getPontuacaoRepository() {return pontuacaoRepository;}
    public List<Pontuacao> listAll() {return getPontuacaoRepository().findAll();}
    public Pontuacao findByIdOrThrowBackBadRequestException(Integer id) {
        return getPontuacaoRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pontuação do time não encontrada"));
    }
    public List<Pontuacao> findByNomeTime(String nomeTime) {
        return pontuacaoRepository.findByNomeTime(nomeTime);
    }
    public void delete(int id) {
        getPontuacaoRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }
    public void deleteAll() {
        getPontuacaoRepository().deleteAll();
    }
    public void replace(PontuacaoPutRequestBody pontuacaoPutRequestBody) {
        Pontuacao pontSalva = findByIdOrThrowBackBadRequestException(pontuacaoPutRequestBody.getId());
        Pontuacao pont = Pontuacao.builder().id(pontSalva.getId())
                .quantGolsFeitos(pontuacaoPutRequestBody.getQuantGolsFeitos()).quantGolsSofridos(pontuacaoPutRequestBody.getQuantGolsSofridos())
                .quantDerrotas(pontuacaoPutRequestBody.getQuantDerrotas()).quantVitorias(pontuacaoPutRequestBody.getQuantVitorias())
                .pont(pontuacaoPutRequestBody.getPont())
                .build();
        getPontuacaoRepository().save(pont);
    }
    public Pontuacao save(PontuacaoPostRequestBody pontuacaoPostRequestBody) {
        return getPontuacaoRepository().save(Pontuacao.builder().nomeTime(pontuacaoPostRequestBody.getNomeTime()).pont(pontuacaoPostRequestBody.getPont())
                .quantGolsFeitos(pontuacaoPostRequestBody.getQuantGolsFeitos()).quantGolsSofridos(pontuacaoPostRequestBody.getQuantGolsSofridos())
                .quantDerrotas(pontuacaoPostRequestBody.getQuantDerrotas()).quantVitorias(pontuacaoPostRequestBody.getQuantVitorias())
                .build());
    }
}
