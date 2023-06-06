package campeonato.exercicio.repository;

import campeonato.exercicio.domain.Jogos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogosRepository extends JpaRepository<Jogos,Integer> {
    List<Jogos> findByNomePart(String nomePart);
}
