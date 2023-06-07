package campeonato.exercicio.pontuacao.repository;

import campeonato.exercicio.pontuacao.domain.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {
    List<Pontuacao> findByNomeTime(String nomeTime);
}
