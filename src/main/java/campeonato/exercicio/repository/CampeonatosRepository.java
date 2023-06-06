package campeonato.exercicio.repository;

import campeonato.exercicio.domain.Campeonatos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampeonatosRepository extends JpaRepository<Campeonatos, Integer> {

    boolean existsByNomeAndAno(String nome,Integer ano);

    boolean existsByTime(String time);

}
