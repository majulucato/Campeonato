package campeonato.exercicio.pontuacao.service;

import campeonato.exercicio.pontuacao.domain.Pontuacao;
import campeonato.exercicio.pontuacao.repository.PontuacaoRepository;
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
}
