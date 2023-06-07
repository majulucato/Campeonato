package campeonato.exercicio.jogo.repository;

import campeonato.exercicio.jogo.domain.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo,Integer> {

}
