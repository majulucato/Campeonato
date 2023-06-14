package campeonato.exercicio.pontuacao.service;

import campeonato.exercicio.pontuacao.domain.Pontuacao;
import campeonato.exercicio.pontuacao.repository.PontuacaoRepository;
import campeonato.exercicio.pontuacao.request.PontuacaoPutRequestBody;
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
    public Pontuacao findByIdOrThrowBackBadRequestException(Long id) {
        return getPontuacaoRepository().findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pontuação do time não encontrada"));
    }
    public List<Pontuacao> findByNomeTime(String nomeTime) {
        return pontuacaoRepository.findByNomeTime(nomeTime);
    }
    public void delete(Long id) {
        getPontuacaoRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }
    public void deleteAll() {
        getPontuacaoRepository().deleteAll();
    }
    public void replace(PontuacaoPutRequestBody pontuacaoPutRequestBody) {
        Pontuacao pontSalva = findByIdOrThrowBackBadRequestException(pontuacaoPutRequestBody.getId());
        Pontuacao partida = Pontuacao.builder().id(pontSalva.getId())
                .quantGolsFeitos(pontuacaoPutRequestBody.getQuantGolsFeitos()).quantGolsSofridos(pontuacaoPutRequestBody.getQuantGolsSofridos())
                .build();
        atualizarPlacar(pontuacaoPutRequestBody);
        getPontuacaoRepository().save(partida);
    }

    public void atualizarPlacar(PontuacaoPutRequestBody pontuacaoPutRequestBody){
        if(pontuacaoPutRequestBody.getQuantGolsFeitos()>pontuacaoPutRequestBody.getQuantGolsSofridos()){
            pontuacaoPutRequestBody.setQuantVitorias(pontuacaoPutRequestBody.getQuantVitorias()+1);
        }
        if(pontuacaoPutRequestBody.getQuantGolsFeitos()==pontuacaoPutRequestBody.getQuantGolsSofridos()){
            pontuacaoPutRequestBody.setQuantEmpates(pontuacaoPutRequestBody.getQuantEmpates()+1);
        }
        if(pontuacaoPutRequestBody.getQuantGolsFeitos()<pontuacaoPutRequestBody.getQuantGolsSofridos()){
            pontuacaoPutRequestBody.setQuantDerrotas(pontuacaoPutRequestBody.getQuantDerrotas()+1);
        }
        pontuacaoPutRequestBody.setQuantJogos(pontuacaoPutRequestBody.getQuantVitorias()+ pontuacaoPutRequestBody.getQuantDerrotas()
                + pontuacaoPutRequestBody.getQuantEmpates());
        pontuacaoPutRequestBody.setPont((pontuacaoPutRequestBody.getQuantVitorias()*3)
                +(pontuacaoPutRequestBody.getQuantEmpates()*1)+(pontuacaoPutRequestBody.getQuantDerrotas()*0));
    }
    /*
    private Long id;
    private String nomeTime;
    private Campeonato campeonato;
    */
}
