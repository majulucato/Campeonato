package campeonato.exercicio.repository;

import campeonato.exercicio.domain.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {
    List<Pontuacao> findByNomeTime(String nomeTime);
}
